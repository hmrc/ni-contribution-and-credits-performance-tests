import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"       %% "performance-test-runner" % "6.2.0",
    "org.playframework" %% "play-ahc-ws-standalone"  % "3.0.7",
    "org.playframework" %% "play-ws-standalone-json" % "3.0.7",
    "org.apache.pekko"  %% "pekko-stream"            % "1.2.0",
    "ch.qos.logback"     % "logback-classic"         % "1.5.18"
  ).map(_ % Test)

}
