package fr.amraneze.logstream.config

import pureconfig._
import pureconfig.generic.auto._

object AppConfig {

	val appConfig: Config = ConfigSource.default.loadOrThrow[Config]

}

case class SparkConfig(master: String, appName: String)

case class Config(spark: SparkConfig, data: DataConfig)

case class DataConfig(input: String, validOutput: String, rejectedOutput: String)
