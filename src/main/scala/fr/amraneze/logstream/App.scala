package fr.amraneze.logstream

import fr.amraneze.logstream.spark.SparkSessionWrapper.sparkSession
import fr.amraneze.logstream.config.JobConfig
import fr.amraneze.logstream.util.ApacheLog
import fr.amraneze.logstream.util.LogUtil.parseLog
import org.apache.spark.sql.{DataFrame, Dataset}

object App {

  import JobConfig._

  def main(args: Array[String]): Unit = {

    val df: DataFrame = sparkSession.read.text(jobConfig.inputPath)

    splitCorrectlyAndWrite(df, jobConfig.validOutputPath, jobConfig.rejectedOutputPath)
  }

  def splitCorrectlyAndWrite(
      inputDf: DataFrame,
      outputValidPath: String,
      outputRejectedPath: String): Unit = {

    import sparkSession.implicits._

    val logs: Dataset[Either[String, ApacheLog]] = inputDf.map { row =>
      parseLog(row.getAs[String](0))
    }

    val validDf: DataFrame = logs.filter(_.isRight).map { _.fold(_ => "", log => log.ip) }.toDF()
    val rejectedDf: DataFrame = logs.filter(_.isLeft).map(_.left.getOrElse("")).toDF()

    validDf.write.text(outputValidPath)
    rejectedDf.write.text(outputRejectedPath)

    println("Main finished", validDf, rejectedDf)

  }

}
