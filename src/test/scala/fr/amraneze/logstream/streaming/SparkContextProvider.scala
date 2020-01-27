package fr.amraneze.logstream.streaming

import java.nio.file.Files
import java.util.UUID

import fr.amraneze.logstream.config.Config.sparkStreamingConfig
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

trait SparkContextProvider {

    def sc: SparkContext

    def appID: String = UUID.randomUUID().toString

    def conf(useManualClock: Boolean): SparkConf = {
        new SparkConf()
          .setMaster(sparkStreamingConfig.master)
          .setAppName(sparkStreamingConfig.appName)
          .set("spark.app.id", appID)
          .set("spark.streaming.clock", getClock(useManualClock))
    }

    def setup(sc: SparkContext): Unit = sc.setCheckpointDir(Files.createTempDirectory(sparkStreamingConfig.appName).toString)

    def getClock(useManualClock: Boolean): String = if (useManualClock) "org.apache.spark.util.ManualClock" else "org.apache.spark.util.SystemClock"

}