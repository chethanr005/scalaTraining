ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "scala-training"
  )

libraryDependencies += "junit" % "junit" % "4.13.2" % Test

libraryDependencies += "org.postgresql" % "postgresql" % "42.3.2"

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % Test

val AkkaHttpVersion = "10.2.9"
val AkkaVersion = "2.6.8"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % AkkaHttpVersion
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5")


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion
)libraryDependencies += "org.postgresql" % "postgresql" % "42.3.2"

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % Test


val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.9"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5","org.slf4j" % "slf4j-simple" % "1.7.5")

libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % AkkaHttpVersion

val AkkaVersion1 = "2.6.8"
val AkkaHttpVersion1 = "10.2.9"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion1,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion1
)