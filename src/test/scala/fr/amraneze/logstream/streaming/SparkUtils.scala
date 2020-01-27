package org.apache.spark.streaming

import org.apache.spark.SparkContext
import org.apache.spark.util.ManualClock

/**
 * Ugly hack to access to Spark Streaming ManuelClock class
 * Check <a href="https://github.com/mkuthan/example-spark/issues/1">scheduler in StreamingContext cannot be accessed #1</a>
 */
object SparkUtils {

    class ClockWrapper {
        def advance(ssc: StreamingContext, timeToAdd: Duration): Unit = {
            ssc.scheduler.clock.asInstanceOf[ManualClock].advance(timeToAdd.milliseconds)
        }
    }

    def stopAllActiveSparkContext(): Unit = SparkContext.getActive.foreach(_.stop())
}