import sbt._

object Depend {
  lazy val scalazVersion = "7.2.16"
  lazy val argonautVersion = "6.2"

  lazy val scalaz = Seq(
    "org.scalaz" %% "scalaz-core"
  ).map(_ % scalazVersion)

  lazy val argonaut = Seq("io.argonaut" %% "argonaut" % argonautVersion)

  lazy val depResolvers = Seq(
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
    Resolver.sonatypeRepo("releases")
  )
}
