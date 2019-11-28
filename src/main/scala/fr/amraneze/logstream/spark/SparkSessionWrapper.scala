package fr.amraneze.logstream.spark

import org.apache.spark.sql.SparkSession
import fr.amraneze.logstream.config.AppConfig.appConfig

object SparkSessionWrapper {

	lazy val sparkSession: SparkSession = {
		SparkSession
			.builder()
			.master(appConfig.spark.master)
			.appName(appConfig.spark.appName)
			.getOrCreate()
	}

}