ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "scala-training"
  )

libraryDependencies += "junit" % "junit" % "4.13" % Test

libraryDependencies += "org.postgresql" % "postgresql" % "42.3.1"
