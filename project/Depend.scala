import sbt._

object Depend {
  lazy val scalazVersion = "7.2.6"
  lazy val argonautVersion = "6.2-RC2"

  lazy val scalaz = Seq(
    "org.scalaz" %% "scalaz-core"
  ).map(_ % scalazVersion)

  lazy val argonaut = Seq("io.argonaut" %% "argonaut" % argonautVersion)

  lazy val scalaTestCheck = Seq(
    "org.scalacheck"  %% "scalacheck" % "1.12.5",
    "org.scalacheck"  %% "scalacheck" % "1.12.5" % "test"
  ).map(_.withSources)

  lazy val depResolvers = Seq(
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
    Resolver.sonatypeRepo("releases")
  )
}
