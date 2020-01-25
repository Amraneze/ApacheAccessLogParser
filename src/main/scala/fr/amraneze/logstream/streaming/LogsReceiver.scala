package fr.amraneze.logstream.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.URL

import fr.amraneze.logstream.config.Config.sparkStreamingConfig
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.internal.Logging

class LogsReceiver extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging {

    def onStart() {
        new Thread("Logs Receiver") {
            override def run() { receive() }
        }.start()
    }

    def onStop() {}

    private def receive() {
        var userInput: String = null
        try {
            val logsUrl = new URL(sparkStreamingConfig.host)
            logInfo(s"Connected to ${sparkStreamingConfig.host}")
            val reader = new BufferedReader(new InputStreamReader(logsUrl.openStream(), "UTF-8"))
            userInput = reader.readLine()
            while(!isStopped && userInput != null) {
                store(userInput)
                userInput = reader.readLine()
            }
            reader.close()
            logInfo("Stopped receiving")
        } catch {
            case e: java.net.ConnectException =>
                restart(s"Error connecting to ${sparkStreamingConfig.host}", e)
            case t: Throwable =>
                restart("Error receiving data", t)
        }
    }
}