package fr.amraneze.logstream.util

import fr.amraneze.logstream.DummyData
import org.scalatest.{FlatSpec, Matchers}

class LogUtilSpec extends FlatSpec with Matchers {

	it should "parse the logs" in {

		val res: Seq[Either[String, ApacheLog]] =
			(DummyData.normalLogs ++ DummyData.badLogsFormat).map(LogUtil.parseLog)

		val expected = DummyData.expectedLogsFormat.map {
			case (_, log) => Right(log)
		} ++ DummyData.badLogsFormat.map(log => Left(log))

		res should contain theSameElementsAs expected

	}

}
