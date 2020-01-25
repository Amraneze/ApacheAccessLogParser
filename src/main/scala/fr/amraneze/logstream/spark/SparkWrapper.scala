package fr.amraneze.logstream.spark

import fr.amraneze.logstream.config.Config.sparkConfig
import fr.amraneze.logstream.streaming.SparkStreamingJob
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.StreamingContext

object SparkWrapper {

  lazy val sparkSession: SparkSession = SparkSession
      .builder()
      .master(sparkConfig.master)
      .appName(sparkConfig.appName)
      .getOrCreate()

  lazy val sparkStreaming: StreamingContext =
    StreamingContext.getActiveOrCreate(new SparkStreamingJob(sparkSession).createStreamingContext)

}
