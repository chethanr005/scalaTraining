ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "scala-training"
  )

libraryDependencies += "junit" % "junit" % "4.13.2" % Test

libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % Test