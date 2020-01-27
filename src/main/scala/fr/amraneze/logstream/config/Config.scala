package fr.amraneze.logstream.config

import pureconfig._
import pureconfig.generic.auto._

object Config {

  private val configObject = ConfigSource.default

  val sparkConfig: SparkConfig = configObject.at("spark").loadOrThrow[SparkConfig]

  val sparkStreamingConfig: SparkStreaming = configObject.at("spark-streaming").loadOrThrow[SparkStreaming]

  val jobConfig: JobConfig = configObject.at("job-config").loadOrThrow[JobConfig]

  val serverConfig: ServerConfig = configObject.at("server-config").loadOrThrow[ServerConfig]

}

case class SparkConfig(master: String, appName: String)

case class SparkStreaming(master: String, appName: String, host: String, batchInterval: Int)

case class Config(spark: SparkConfig, jobConfig: JobConfig)

case class JobConfig(inputPath: String, validOutputPath: String, rejectedOutputPath: String)

case class ServerConfig(path: String, port: Int)
