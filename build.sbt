import scala.util.Try

lazy val commonSettings = Seq(
  organization := "com.ii",
  scalaVersion := "2.11.8",
  resolvers := Depend.depResolvers,
  version := Try(sys.env("LIB_VERSION")).getOrElse("0.0.1"),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  bintrayOrganization := Some("imageintelligence"),
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

lazy val core = Project(
  id = "core",
  base = file("core"),
  settings = commonSettings ++ Seq (
    name := "galahad-core",
    version := "0.0.1",
    libraryDependencies :=
      Depend.scalaz ++
      Depend.scalaTestCheck
  )
)

lazy val argonaut = Project(
  id = "argonaut",
  base = file("argonaut"),
  settings = commonSettings ++ Seq(
    name := "galahad-argonaut",
    version := "0.0.1",
    libraryDependencies :=
      Depend.scalaz ++
      Depend.argonaut ++
      Depend.scalaTestCheck
  )
).dependsOn(core % "test->test;compile->compile")

lazy val root = (project in file(".")).settings (publish := { }).aggregate(core, argonaut)
