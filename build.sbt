ThisBuild / organization      := "com.colisweb"
ThisBuild / scalaVersion      := "2.12.8"
ThisBuild / scalafmtOnCompile := true
ThisBuild / scalafmtCheck     := true
ThisBuild / scalafmtSbtCheck  := true

lazy val projectName = "safe-libphonenumber"

lazy val testKitLibs = Seq(
  "org.scalacheck" %% "scalacheck" % "1.14.0",
  "org.scalactic"  %% "scalactic"  % "3.0.5",
  "org.scalatest"  %% "scalatest"  % "3.0.5",
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
    .settings(
      libraryDependencies ++= Seq(
        "com.googlecode.libphonenumber" % "libphonenumber" % "8.10.5"
      ) ++ testKitLibs)

lazy val jruby =
  project
    .settings(moduleName := s"jruby-$projectName")
    .dependsOn(core)

/**
  * Copied from Cats
  */
lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

inThisBuild(
  List(
    credentials += Credentials(Path.userHome / ".bintray" / ".credentials"),
    licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/https://opensource.org/licenses/Apache-2.0")),
    homepage := Some(url("https://github.com/colisweb/safe-libphonenumber")),
    bintrayOrganization := Some("colisweb"),
    bintrayReleaseOnPublish := true,
    publishMavenStyle := true,
    pomExtra := (
      <scm>
        <url>git@github.com:colisweb/safe-libphonenumber.git</url>
        <connection>scm:git:git@github.com:colisweb/safe-libphonenumber.git</connection>
      </scm>
        <developers>
          <developer>
            <id>guizmaii</id>
            <name>Jules Ivanic</name>
          </developer>
        </developers>
    )
  )
)
