package fr.amraneze.logstream

import fr.amraneze.logstream.spark.SparkSessionWrapper.sparkSession
import fr.amraneze.logstream.config.AppConfig.appConfig
import fr.amraneze.logstream.util.LogUtil.parseLog
import org.apache.spark.sql.DataFrame

object App {

  def main(
      args: Array[String])
    : Unit = {
	val logsPath =
      if (args.length == 0)
        appConfig.data.input
      else args(0)
    val df: DataFrame =
      sparkSession.read.text(
        logsPath)
    splitCorrectlyAndWrite(
      df,
      appConfig.data.validOutput,
      appConfig.data.rejectedOutput)
  }

  def splitCorrectlyAndWrite(
      inputDf: DataFrame,
      outputValidPath: String,
      outputRejectedPath: String)
    : Unit = {

    import sparkSession.implicits._

    val logs = inputDf.map {
      row =>
        parseLog(
          row.getAs[String](
            0))
    }

    val validDf: DataFrame =
      logs
        .filter(_.isRight)
        .map {
          _.fold(
            _ => "",
            log => log.ip)
        }
        .toDF()
    val rejectedDf
      : DataFrame = logs
      .filter(_.isLeft)
      .map(
        _.left.getOrElse(""))
      .toDF()

    validDf.write.text(
      outputValidPath)
    rejectedDf.write.text(
      outputRejectedPath)

    println("Main finished",
            validDf,
            rejectedDf)

  }

}
