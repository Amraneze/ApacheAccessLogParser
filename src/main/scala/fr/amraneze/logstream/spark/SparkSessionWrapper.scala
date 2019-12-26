package fr.amraneze.logstream.spark

import fr.amraneze.logstream.config.JobConfig
import org.apache.spark.sql.SparkSession

object SparkSessionWrapper {

  lazy val sparkSession: SparkSession = {
    SparkSession
      .builder()
      .master(JobConfig.sparkConfig.master)
      .appName(JobConfig.sparkConfig.appName)
      .getOrCreate()
  }

}
