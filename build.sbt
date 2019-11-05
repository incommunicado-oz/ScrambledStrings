import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0"

parallelExecution in ThisBuild := false

lazy val root = (project in file("."))
  .settings(
    name := "scrambled-strings",
    mainClass in assembly := Some("scrambled.Main"),
    assemblyJarName in assembly := "ScrambledStrings.jar",
    libraryDependencies ++= Seq(
      logbackClassic,
      scalaLogging,
      scalaTest % Test,
      scoptConfig
  ))
