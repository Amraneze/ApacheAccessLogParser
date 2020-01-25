package fr.amraneze.logstream.streaming

import fr.amraneze.logstream.config.Config.sparkStreamingConfig
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._

class SparkStreamingJob(sparkSession: SparkSession) {

    def createStreamingContext(): StreamingContext = new StreamingContext(sparkSession.sparkContext, Seconds(sparkStreamingConfig.batchInterval))

}