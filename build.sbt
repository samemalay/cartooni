name := """cartooni"""

version := "1.0"

lazy val cartooni = (project in file("."))
	.enablePlugins(PlayScala, LauncherJarPlugin)

enablePlugins(DockerPlugin)

scalaVersion := "2.12.8"

val akkaHTTPVersion = "10.1.7"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.akka"      %% "akka-http-core"      % akkaHTTPVersion,
  "org.scalatestplus.play" %% "scalatestplus-play"  % "3.1.1" % Test,
  "org.mockito"             % "mockito-all"               % "1.10.19"         % Test
)

scalacOptions ++= Seq("-deprecation", "-feature")

enablePlugins(UniversalPlugin)

topLevelDirectory in Universal := None

import NativePackagerHelper._
import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.packager.docker.DockerChmodType

javaOptions in Universal ++= Seq(
  "-Dpidfile.path=/dev/null"
)

dockerExposedPorts := Seq(9000)
dockerBaseImage := "openjdk:8"
dockerExposedVolumes := Seq("/opt/docker/logs")
dockerChmodType := DockerChmodType.UserGroupWriteExecute
