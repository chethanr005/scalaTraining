ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "scala-training"
  )


libraryDependencies += "junit" % "junit" % "4.13.2" % Test

libraryDependencies += "org.postgresql" % "postgresql" % "42.3.2"


val AkkaHttpVersion = "10.2.9"
val AkkaVersion     = "2.8.3"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
"com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test
)

libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % AkkaHttpVersion
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5", "org.slf4j" % "slf4j-simple" % "1.7.5")


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9"
//libraryDependencies += "org.mockito" % "mockito-all" % "2.0.2-beta" % Test
libraryDependencies += "org.specs2" %% "specs2-core" % "4.3.0" % Test
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
  "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % Test
)
libraryDependencies += "com.github.tminglei" %% "slick-pg" % "0.21.1"
libraryDependencies += "com.github.tminglei" %% "slick-pg_play-json" % "0.21.1"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.4.1"
libraryDependencies += "org.mongodb" % "mongodb-driver-sync" % "4.8.1"


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion
