name := "inUse"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, RiffRaffArtifact)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "joda-time" % "joda-time" % "2.9.4",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.10.62",
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.24",
  "org.cvogt" %% "play-json-extensions" % "0.6.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

riffRaffPackageType := (packageZipTarball in Universal).value

def env(key: String): Option[String] = Option(System.getenv(key))
riffRaffBuildIdentifier := env("CIRCLE_BUILD_NUM").getOrElse("DEV")
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")
riffRaffPackageName := s"editorial-tools:${name.value}"
riffRaffManifestProjectName := riffRaffPackageName.value