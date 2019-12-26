package fr.amraneze.logstream

import fr.amraneze.logstream.util.ApacheLog

object DummyData {

  val normalLogs: Seq[String] =
    """
		  |77.179.66.156 - - [07/Dec/2016:10:43:23 +0100] "GET /test1 HTTP/1.1" 404 571 "-" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36"
		  |207.46.13.150 - - [13/Feb/2016:12:47:35 +0100] "GET /index.php?option=com_content&view=frontpage HTTP/1.1" 200 9644 "-" "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53 (compatible; bingbot/2.0;  http://www.bing.com/bingbot.htm)" "-"
		  |88.198.140.4 - - [14/Feb/2016:07:53:45 +0100] "GET / HTTP/1.1" 200 10479 "http://www.webwiki.at/www.almhuette-raith.at" "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0" "-"
		  |188.169.186.251 - - [18/Feb/2016:06:29:17 +0100] "POST /administrator/index.php HTTP/1.1" 200 4494 "http://almhuette-raith.at/administrator/" "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36" "-"
		  |77.75.77.72 - - [13/Feb/2016:12:46:55 +0100] "GET /robots.txt HTTP/1.1" 200 304 "-" "Mozilla/5.0 (compatible; SeznamBot/3.2; +http://fulltext.sblog.cz/)" "-"
		  |205.167.170.15 - - [10/Feb/2016:17:43:44 +0100] "GET /images/phocagallery/almhuette/thumbs/phoca_thumb_m_zimmer.jpg HTTP/1.1" 200 4320 "-" "Go-http-client/1.1" "-"
		""".stripMargin.split("\n").toSeq.map(_.trim).filter(_ != "")

  val expectedLogsFormat: Map[String, ApacheLog] = Map(
    "77.179.66.156" -> ApacheLog(
      "77.179.66.156",
      "07/Dec/2016",
      10,
      43,
      23,
      "+0100",
      "GET",
      "/test1",
      "HTTP/1.1",
      404,
      "571",
      "-",
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36",
      null
    ),
    "207.46.13.150" -> ApacheLog(
      "207.46.13.150",
      "13/Feb/2016",
      12,
      47,
      35,
      "+0100",
      "GET",
      "/index.php?option=com_content&view=frontpage",
      "HTTP/1.1",
      200,
      "9644",
      "-",
      "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53 (compatible; bingbot/2.0;  http://www.bing.com/bingbot.htm)",
      "-"
    ),
    "88.198.140.4" -> ApacheLog(
      "88.198.140.4",
      "14/Feb/2016",
      7,
      53,
      45,
      "+0100",
      "GET",
      "/",
      "HTTP/1.1",
      200,
      "10479",
      "http://www.webwiki.at/www.almhuette-raith.at",
      "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0",
      "-"
    ),
    "188.169.186.251" -> ApacheLog(
      "188.169.186.251",
      "18/Feb/2016",
      6,
      29,
      17,
      "+0100",
      "POST",
      "/administrator/index.php",
      "HTTP/1.1",
      200,
      "4494",
      "http://almhuette-raith.at/administrator/",
      "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36",
      "-"
    ),
    "77.75.77.72" -> ApacheLog(
      "77.75.77.72",
      "13/Feb/2016",
      12,
      46,
      55,
      "+0100",
      "GET",
      "/robots.txt",
      "HTTP/1.1",
      200,
      "304",
      "-",
      "Mozilla/5.0 (compatible; SeznamBot/3.2; +http://fulltext.sblog.cz/)",
      "-"
    ),
    "205.167.170.15" -> ApacheLog(
      "205.167.170.15",
      "10/Feb/2016",
      17,
      43,
      44,
      "+0100",
      "GET",
      "/images/phocagallery/almhuette/thumbs/phoca_thumb_m_zimmer.jpg",
      "HTTP/1.1",
      200,
      "4320",
      "-",
      "Go-http-client/1.1",
      "-"
    )
  )

  val badLogsFormat: Seq[String] =
    """
		  |77.75.77.72 [13/Feb/2016:12:46:55 +0100] "GET /robots.txt HTTP/1.1" 200 304 "-" "Mozilla/5.0 (compatible; SeznamBot/3.2; +http://fulltext.sblog.cz/)" "-"
		  |- - [10/Feb/2016:17:43:44 +0100] "GET /images/phocagallery/almhuette/thumbs/phoca_thumb_m_zimmer.jpg HTTP/1.1" 200 4320 "-" "Go-http-client/1.1" "-"
		  |205.167.170.15 - - [10/Feb/2016:17:43:43 +0100] 200 8408 "-" "Go-http-client/1.1" "-"
		""".stripMargin.split("\n").toSeq.map(_.trim).filter(_ != "")

  val dummyLogs = normalLogs ++ badLogsFormat

}
