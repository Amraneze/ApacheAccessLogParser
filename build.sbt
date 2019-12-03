name := "ApacheAccessLogParser"
version := "0.1"

/*
  Spark doesn’t work with Scala 2.13
  SBT 1.x uses Scala 2.13, so it’s best to stick with SBT 0.13.x when using Spark.
 */
//scalaVersion := "2.13.1"
scalaVersion := "2.12.8"
val sparkVersion = "2.4.3"
val sparkTestingBaseVersion =
  "0.12.0"

libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig"         % "0.12.1",
  "org.apache.spark"      %% "spark-core"         % s"$sparkVersion",
  "org.apache.spark"      %% "spark-sql"          % s"$sparkVersion",
  "com.holdenkarau"       %% "spark-testing-base" % s"${sparkVersion}_$sparkTestingBaseVersion" % "test",
  "org.scalatest"         %% "scalatest"          % "3.0.1" % "test"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF",
                xs @ _*) =>
    xs map { _.toLowerCase } match {
      case "manifest.mf" :: Nil |
          "index.list" :: Nil |
          "dependencies" :: Nil =>
        MergeStrategy.discard
      case ps @ _ :: _
          if ps.last.endsWith(
            ".sf") || ps.last
            .endsWith(
              ".dsa") =>
        MergeStrategy.discard
      case "plexus" :: _ =>
        MergeStrategy.discard
      case "services" :: _ =>
        MergeStrategy.filterDistinctLines
      case "spring.schemas" :: Nil |
          "spring.handlers" :: Nil =>
        MergeStrategy.filterDistinctLines
      case _ =>
        MergeStrategy.first
    }
  case "application.conf" =>
    MergeStrategy.concat
  case "reference.conf" =>
    MergeStrategy.concat
  case _ =>
    MergeStrategy.first
}
