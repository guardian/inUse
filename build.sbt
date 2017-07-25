name := "in-use"

lazy val root = (project in file(".")).enablePlugins(PlayScala, RiffRaffArtifact, SbtWeb, JDebPackaging)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "joda-time" % "joda-time" % "2.9.4",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.10.62",
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.24",
  "org.cvogt" %% "play-json-extensions" % "0.6.0",
  "com.google.code.gson" % "gson" % "1.7.1",
  "org.apache.commons" % "commons-csv" % "1.1"

)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

import com.typesafe.sbt.packager.archetypes.ServerLoader.Systemd
serverLoading in Debian := Systemd

riffRaffPackageType := (packageBin in Debian).value

def env(key: String): Option[String] = Option(System.getenv(key))
name in Universal := normalizedName.value
topLevelDirectory := Some(normalizedName.value)
riffRaffBuildIdentifier := env("CIRCLE_BUILD_NUM").getOrElse("DEV")
riffRaffUploadArtifactBucket := Option("riffraff-artifact")
riffRaffUploadManifestBucket := Option("riffraff-builds")
riffRaffPackageName := name.value
riffRaffManifestProjectName := s"editorial-tools:${name.value}"
riffRaffPackageType := (packageBin in Debian).value
riffRaffArtifactResources ++= Seq(
  baseDirectory.value / "riff-raff.yaml" -> "riff-raff.yaml",
  riffRaffPackageType.value -> s"${name.value}/${name.value}.deb",
  baseDirectory.value / "cloudformation" / "inUse.yaml" -> "cloudformation/inUse.yaml"
)
javaOptions in Universal ++= Seq(
  "-Dpidfile.path=/dev/null"
)
debianPackageDependencies := Seq("openjdk-8-jre-headless")
maintainer := "digitial tools team <digitalcms.dev@guardian.co.uk>"
packageSummary := "inUse"
packageDescription := """service usage tracking"""