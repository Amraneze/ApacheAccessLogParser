package fr.amraneze.logstream.util

import scala.util.matching.Regex

case class ApacheLog(ip: String,
					 date: String,
					 hour: Int,
					 min: Int,
					 sec: Int,
					 timeZone: String,
					 methodType: String,
					 uri: String,
					 protocol: String,
					 responseStatus: Int,
					 size: String,
					 referer: String,
					 userAgent: String,
					 forward: String = "")

object LogUtil {

	// For more detailed explanation check this website https://regexr.com/4pdv6
	private val logRegex: Regex =
		"""^(\S+) - - \[(\S+):(\S+):(\S+):(\S+) ([+|-]\S+)\] "(\S+) (\S+) (\S+)" (\d{3}|-) (\S+) "(\S+)" "([^"]*)"?\s?"??(?:[^"]*)?"?(?:\s??(\S+)?")$""".r

	def parseLog(line: String): Either[String, ApacheLog] = {
		if (logIsNonMatched(line))
			return Left(line)
		val logRegex(ip,
		date,
		hour,
		min,
		sec,
		timeZone,
		methodType,
		uri,
		protocol,
		responseStatus,
		size,
		referer,
		userAgent,
		forward) = line
		Right(
			ApacheLog(ip,
				date,
				hour.toInt,
				min.toInt,
				sec.toInt,
				timeZone,
				methodType,
				uri,
				protocol,
				responseStatus.toInt,
				size,
				referer,
				userAgent,
				forward))
	}

	private def logIsMatching(line: String): Boolean = {
		logRegex.pattern.matcher(line).matches
	}

	private def logIsNonMatched(line: String): Boolean = {
		!logRegex.pattern.matcher(line).matches
	}
}
