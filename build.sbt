import scala.util.Try

lazy val commonSettings = Seq(
  organization := "com.imageintelligence",
  scalaVersion := "2.12.4",
  resolvers := Depend.depResolvers,
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  bintrayOrganization := Some("imageintelligence"),
  crossScalaVersions := Seq("2.11.0", "2.12.0"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture",
    "-Yrangepos"
  )
)

lazy val core =
  (project in file("core"))
  .settings(
    commonSettings,
    Seq (
      name := "galahad-core",
      version := Try(sys.env("LIB_VERSION")).getOrElse("0.0.1"),
      libraryDependencies :=
        Depend.scalaz
    )
  )

lazy val argonaut =
  (project in file("argonaut"))
  .settings(
    commonSettings,
    Seq (
      name := "galahad-argonaut",
      version := Try(sys.env("LIB_VERSION")).getOrElse("0.0.1"),
      libraryDependencies :=
        Depend.scalaz ++
        Depend.argonaut
    )
  ).dependsOn(core % "test->test;compile->compile")

lazy val root = (project in file(".")).settings (publish := { }).aggregate(core, argonaut)
