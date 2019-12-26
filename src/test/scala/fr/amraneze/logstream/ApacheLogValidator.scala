package fr.amraneze.logstream

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import fr.amraneze.logstream.DummyData._
import java.nio.file.Files

import fr.amraneze.logstream.util.LogUtil
import org.apache.spark.sql.{DataFrame, SparkSession}

class ApacheLogValidator
    extends FlatSpec
    with Matchers
    with SharedSparkContext
    with BeforeAndAfterAll {

  private val tmpFile = Files.createTempDirectory("apache-log-")

  private val inputPath = s"$tmpFile/amrane/input"
  private val outputValidPath = s"$tmpFile/amrane/output_valid"
  private val outputRejectedPath = s"$tmpFile/amrane/output_rejected"

  it should "split correctly" in {

    val sqlContext = SparkSession.builder().getOrCreate()
    import sqlContext.implicits._

    val inputLogDf = sc.parallelize(dummyLogs).coalesce(1).toDF()

    inputLogDf.write.text(inputPath)
    App.splitCorrectlyAndWrite(sqlContext.read.text(inputPath), outputValidPath, outputRejectedPath)

    val rejectedData = sqlContext.read.text(outputRejectedPath)
    val validData = sqlContext.read.text(outputValidPath)

    val rejectedDf: DataFrame = sc.parallelize(badLogsFormat).toDF()
    val validDf: DataFrame =
      sc.parallelize(normalLogs.map(line => LogUtil.parseLog(line).map(_.ip).getOrElse(""))).toDF()

    rejectedData.except(rejectedDf).count() shouldBe 0
    validData.except(validDf).count() shouldBe 0

  }

}
