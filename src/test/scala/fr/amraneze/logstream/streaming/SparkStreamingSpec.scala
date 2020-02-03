package fr.amraneze.logstream.streaming

import fr.amraneze.logstream.DummyData.{expectedLogsFormatList, graphLogs, normalLogs}
import fr.amraneze.logstream.config.Config.sparkStreamingConfig
import fr.amraneze.logstream.util.{ApacheLog, Graph, GraphChildren, GraphValue}
import fr.amraneze.logstream.util.LogUtil.parseLog
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.SparkUtils.ClockWrapper
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.{Logging, SparkContext}
import org.apache.spark.streaming.{Seconds, SparkUtils, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FlatSpec, GivenWhenThen, Matchers}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class SparkStreamingSpec extends FlatSpec with Matchers with BeforeAndAfterAll with BeforeAndAfterEach with GivenWhenThen with Logging with SparkContextProvider {

  private var ssc: StreamingContext        = _
  private var batchListener: BatchListener = _
  @transient private var _sc: SparkContext = _
  private var useManualClock: Boolean      = true

  // Timeout that we will wait until we will
  // get the event to the batch listener
  private val timeout: Int                    = 2000
  private lazy val clockWrapper: ClockWrapper = new ClockWrapper()

  override def beforeAll(): Unit = SparkUtils.stopAllActiveSparkContext()

  override def sc: SparkContext = _sc

  override def beforeEach(): Unit = {
    _sc = new SparkContext(conf(useManualClock))
    setup(sc)

    ssc = StreamingContext.getActiveOrCreate(initSparkStream)
    batchListener = new BatchListener(ssc)
    def initSparkStream(): StreamingContext = new StreamingContext(sc, Seconds(sparkStreamingConfig.batchInterval))

    super.beforeEach()
  }

  override def afterEach(): Unit = {
    try {
      Option(ssc).foreach(_.stop())
      Option(_sc).foreach(_.stop())
      ssc = null
      _sc = null
      System.clearProperty("spark.streaming.clock")
    } finally {
      super.afterEach()
    }

  }

  override def afterAll(): Unit = {}

  it should "parse logs each seconds" in {
    Given("Spark streaming context is initialized")
    val logsRDD: mutable.Queue[RDD[String]] = mutable.Queue[RDD[String]]()

    var results: ListBuffer[Array[ApacheLog]] = ListBuffer.empty[Array[ApacheLog]]

    def collectLogs(logs: InputDStream[String])(callback: Array[ApacheLog] => ListBuffer[Array[ApacheLog]]): Unit =
      logs
        .foreachRDD { rdd =>
          callback(
            rdd
              .map(parseLog)
              .filter(_.isRight)
              .map {
                case Right(x) => x
              }
              .collect())
        }

    collectLogs(ssc.queueStream(logsRDD)) { logs: Array[ApacheLog] =>
      results += logs
    }

    ssc.start()

    When("first set of two logs are queued")
    logsRDD += sc.makeRDD(normalLogs.slice(0, 2))

    Then("The first two logs parsed after first slide")
    clockWrapper.advance(ssc, Seconds(sparkStreamingConfig.batchInterval))
    batchListener.waitUntilBatchesCompleted(1, timeout)
    results.last should equal(expectedLogsFormatList.slice(0, 2))

    When("second set of two logs queued")
    logsRDD += sc.makeRDD(normalLogs.slice(2, 4))

    Then("The second row of two logs parsed after second slide")
    clockWrapper.advance(ssc, Seconds(sparkStreamingConfig.batchInterval))
    batchListener.waitUntilBatchesCompleted(2, timeout)
    results.last should equal(expectedLogsFormatList.slice(2, 4))

    When("Nothing is queued")

    Then("The result needs to be the same as last slide")
    clockWrapper.advance(ssc, Seconds(sparkStreamingConfig.batchInterval))
    results.last should equal(expectedLogsFormatList.slice(2, 4))
  }

  it should "parse logs and display it for graph" in {
    Given("Spark streaming context is initialized")
    val logsRDD: mutable.Queue[RDD[String]] = mutable.Queue[RDD[String]]()

    type GraphLogs = Array[Graph]

    var results: ListBuffer[GraphLogs] = ListBuffer.empty[GraphLogs]

    def collectLogs(logs: InputDStream[String])(callback: GraphLogs => ListBuffer[GraphLogs]): Unit =
      logs
        .foreachRDD { rdd =>
          callback(
            rdd
              .map(parseLog)
              .filter(_.isRight)
              .map {
                case Right(x) => x
              }
              .map(log => (log.ip, log.uri))
              .groupBy(_._1)
              .mapValues(_.map(tuple => GraphValue(tuple._1, tuple._2)))
              .map(n => GraphChildren(n._1, n._2.toArray))
              .collect()
              .asInstanceOf[GraphLogs])
        }

    collectLogs(ssc.queueStream(logsRDD)) { logs: GraphLogs =>
      results += logs
    }

    ssc.start()

    When("first set of two logs are queued")
    logsRDD += sc.makeRDD(normalLogs.slice(0, 2))

    Then("The first two logs parsed after first slide")
    clockWrapper.advance(ssc, Seconds(sparkStreamingConfig.batchInterval))
    batchListener.waitUntilBatchesCompleted(1, timeout)

    results.last.sortBy(_.name).foreach(result => {
        val expectedGraphLog = graphLogs.get(result.name)
        expectedGraphLog.isDefined shouldBe true
        result.children should contain theSameElementsAs expectedGraphLog.get.children
    })
  }
}
