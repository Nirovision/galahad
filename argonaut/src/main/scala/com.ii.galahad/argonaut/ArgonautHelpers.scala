package com.ii.galahad.argonaut

import java.net.URL

import argonaut._
import argonaut.Argonaut._
import java.util.UUID
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scalaz._, Scalaz._

object ArgonautHelpers {

  def eitherDecoder[A, B](history: CursorHistory, f: A => String \/ B): A => DecodeResult[B] = {
    f(_).fold(x => DecodeResult.fail[B](x, history), _.pure[DecodeResult])
  }

  implicit def URLCodecJson: CodecJson[URL] =
    CodecJson(
      i => i.toString.asJson,
      i => i.as[String].flatMap(eitherDecoder(
        i.history,
        a => \/.fromTryCatchNonFatal(new URL(a)).leftMap(_ => "Could not decode URL")
      ))
    )

  implicit def UUIDCodecJson: CodecJson[UUID] =
    CodecJson(
      i => i.toString.asJson,
      i => i.as[String].flatMap(eitherDecoder(
        i.history,
        a => \/.fromTryCatchNonFatal(UUID.fromString(a)).leftMap(_ => "Could not decode UUID")
      ))
    )


  implicit def DurationEncodeJson : CodecJson[Duration] = {
    CodecJson(
      i => i.toMillis.asJson,
      i => i.as[Long].map(Duration.create(_, TimeUnit.MILLISECONDS))
    )
  }

}
