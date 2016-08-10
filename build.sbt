name := "inUse"

lazy val root = (project in file(".")).enablePlugins(PlayScala, RiffRaffArtifact, SbtWeb)

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
name in Universal := normalizedName.value
topLevelDirectory := Some(normalizedName.value)
riffRaffBuildIdentifier := env("CIRCLE_BUILD_NUM").getOrElse("DEV")
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")
riffRaffPackageName := s"editorial-tools:${name.value}"
riffRaffManifestProjectName := riffRaffPackageName.value
riffRaffPackageType := (packageZipTarball in config("universal")).value
riffRaffArtifactResources ++= Seq(
  riffRaffPackageType.value -> s"packages/${name.value}/${name.value}.tgz"
)