package fr.amraneze.logstream.streaming

import org.apache.spark.streaming.scheduler.{StreamingListener, StreamingListenerBatchCompleted}
import org.apache.spark.streaming.{StreamingContext, Time}

class BatchListener(ssc: StreamingContext) {

    private var numCompletedBatches: Int = 0
    private var lastCompletedBatchTime: Time = _

    private val listener = new StreamingListener {
        override def onBatchCompleted(batchCompleted: StreamingListenerBatchCompleted): Unit =
            BatchListener.this.synchronized {
                numCompletedBatches += 1
                lastCompletedBatchTime = batchCompleted.batchInfo.batchTime
                BatchListener.this.notifyAll()
            }
    }

    ssc.addStreamingListener(listener)

    def getNumCompletedBatches: Int = this.synchronized {
        numCompletedBatches
    }

    def getLastCompletedBatchTime: Time = this.synchronized {
        lastCompletedBatchTime
    }

    def waitUntilBatchesCompleted(expectedNumCompletedBatches: Int, timeout: Long): Boolean =
        waitUntil(numCompletedBatches >= expectedNumCompletedBatches, timeout)

    private def waitUntil(condition: => Boolean, timeout: Long): Boolean = {
        synchronized {
            var now = System.currentTimeMillis()
            val timeoutTick = now + timeout
            while (!condition && timeoutTick > now) {
                wait(timeoutTick - now)
                now = System.currentTimeMillis()
            }
            condition
        }
    }

}