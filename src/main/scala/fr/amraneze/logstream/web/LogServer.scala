package fr.amraneze.logstream.web

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpEntity
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, Sink, Source}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.OverflowStrategy
import fr.amraneze.logstream.config.Config.serverConfig
import fr.amraneze.logstream.spark.SparkWrapper._
import fr.amraneze.logstream.streaming.LogsReceiver
import fr.amraneze.logstream.util.ApacheLog
import fr.amraneze.logstream.util.LogUtil.parseLog
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.DStream
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val apacheLogFormat: RootJsonFormat[ApacheLog] = jsonFormat14(ApacheLog)
  implicit object logsFormat extends RootJsonFormat[Array[ApacheLog]] {
    def write(set: Array[ApacheLog]) = JsArray(set.map(_.toJson).toVector)
    def read(json: JsValue): Array[ApacheLog] = json match {
      case JsArray(elements) => elements.map(_.convertTo[ApacheLog]).toArray
      case x                 => deserializationError("Expected Set as JsArray, but got " + x)
    }
  }
}

object LogServer extends App with Directives with JsonSupport {

  implicit val system: ActorSystem = ActorSystem("log-server")

  val logs: DStream[ApacheLog] = sparkStreaming
    .receiverStream(new LogsReceiver())
    .map(parseLog)
    .filter(_.isRight)
    .map {
      case Right(log) => log
    }
    .window(Seconds(1))
    .cache()

  private val producer: Source[Array[ApacheLog], NotUsed] = Source
    .actorRef[RDD[ApacheLog]](256, OverflowStrategy.dropHead)
    .mapMaterializedValue[Unit] { actorRef =>
      logs.foreachRDD(actorRef ! _)
    }
    .map((rdd: RDD[ApacheLog]) => rdd.collect())
    .toMat(BroadcastHub.sink(bufferSize = 256))(Keep.right)
    .run()

  producer.runWith(Sink.ignore)

  private val logFlowHandler: Flow[Message, Message, NotUsed] =
    Flow[Message]
      .mapConcat(_ => Seq.empty.toMap)
      .merge(producer)
      .filter(_.isInstanceOf[Array[ApacheLog]])
      .map {
        case logs: Array[ApacheLog] => logsFormat.write(logs).toString()
      }
      .map(logs => TextMessage(logs))

  val route: Route = {
    pathPrefix("api") {
      path("start") {
        handleWebSocketMessages(logFlowHandler)
      } ~
        path("stop") {
          import system.dispatcher

          sparkStreaming.stop(stopSparkContext = true, stopGracefully = true)
          httpBinding.flatMap(_.unbind()).onComplete(_ => system.terminate())
          complete(HttpEntity("ok"))
        }
    }
  }

  lazy val httpBinding = Http().bindAndHandle(route, serverConfig.path, serverConfig.port)
  httpBinding

  sparkStreaming.start()
  sparkStreaming.awaitTermination()
}
