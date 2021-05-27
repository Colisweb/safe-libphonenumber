import CompileFlags.crossScalacOptions

lazy val scala212               = "2.12.11"
lazy val scala213               = "2.13.5"
lazy val supportedScalaVersions = List(scala213, scala212)

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / organization      := "com.colisweb"
ThisBuild / scalaVersion      := scala213
ThisBuild / scalafmtOnCompile := true
ThisBuild / scalafmtCheck     := true
ThisBuild / scalafmtSbtCheck  := true
ThisBuild / pushRemoteCacheTo := Some(
  MavenCache("local-cache", baseDirectory.value / sys.env.getOrElse("CACHE_PATH", "sbt-cache"))
)
scalacOptions ~= (_.filterNot(Set("-Ywarn-inaccessible")))
crossScalaVersions := supportedScalaVersions

lazy val projectName = "safe-libphonenumber"

lazy val testKitLibs = Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.0",
  "org.scalactic"  %% "scalactic"  % "3.2.9",
  "org.scalatest"  %% "scalatest"  % "3.2.9",
).map(_ % Test)

lazy val root =
  Project(id = projectName, base = file("."))
    .settings(moduleName := "root")
    .settings(noPublishSettings: _*)
    .aggregate(core, jruby)
    .dependsOn(core, jruby)

lazy val core =
  project
    .settings(moduleName := projectName)
    .settings(crossScalaVersions := supportedScalaVersions)
    .settings(
      libraryDependencies ++= Seq(
        "com.googlecode.libphonenumber" % "libphonenumber" % "8.10.5"
      ) ++ testKitLibs)

lazy val jruby =
  project
    .settings(moduleName := s"jruby-$projectName")
    .settings(crossScalaVersions := supportedScalaVersions)
    .dependsOn(core)

/**
  * Copied from Cats
  */
lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)
