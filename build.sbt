enablePlugins(JavaAppPackaging, DockerPlugin)

name := "ApacheAccessLogParser"
version := "0.1"

mainClass in Compile := Some("fr.amraneze.logstream.App")

/*
  Spark doesn’t work with Scala 2.13
  SBT 1.x uses Scala 2.13, so it’s best to stick with SBT 0.13.x when using Spark.
 */
scalaVersion := "2.12.8"
val sparkVersion = "2.4.3"
val sparkTestingBaseVersion =
  "0.12.0"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig"         % "0.12.1",
  "org.apache.spark"      %% "spark-core"         % s"$sparkVersion",
  "org.apache.spark"      %% "spark-sql"          % s"$sparkVersion",
  "org.apache.spark"      %% "spark-streaming"    % s"$sparkVersion",
  "com.typesafe.akka"     %% "akka-http"          % "10.1.11",
  "ch.megard"             %% "akka-http-cors"     % "0.4.2",
  "com.typesafe.akka"     %% "akka-stream"        % "2.6.1",
  "com.typesafe.akka"     %% "akka-http-spray-json" % "10.1.11",
  "com.holdenkarau"       %% "spark-testing-base" % s"${sparkVersion}_$sparkTestingBaseVersion" % "test",
  "org.scalatest"         %% "scalatest"          % "3.0.1" % "test"
)

// Docker config
maintainer := "Amrane Ait Zeouay <a.zeouayamran@gmail.com>"
