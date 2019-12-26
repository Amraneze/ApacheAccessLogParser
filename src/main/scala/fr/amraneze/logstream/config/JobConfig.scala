package fr.amraneze.logstream.config

import pureconfig._
import pureconfig.generic.auto._

object JobConfig {

  private val configObject = ConfigSource.default

  val sparkConfig: SparkConfig = configObject.at("spark").loadOrThrow[SparkConfig]

  val jobConfig: JobConfig = configObject.at("job-config").loadOrThrow[JobConfig]

}

case class SparkConfig(master: String, appName: String)

case class Config(spark: SparkConfig, jobConfig: JobConfig)

case class JobConfig(inputPath: String, validOutputPath: String, rejectedOutputPath: String)
