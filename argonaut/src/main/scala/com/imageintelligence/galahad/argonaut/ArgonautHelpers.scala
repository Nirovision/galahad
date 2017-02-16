package com.imageintelligence.galahad.argonaut

import java.net.URL
import java.time.Instant

import argonaut._
import argonaut.Argonaut._
import java.util.UUID
import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scalaz._
import Scalaz._

object ArgonautHelpers {

  def fromFoldable[F[_], A](implicit A: EncodeJson[A], F: Foldable[F]): EncodeJson[F[A]] =
    EncodeJson(fa => jArray(F.foldLeft(fa, Nil: List[Json])((list, a) => A.encode(a) :: list).reverse))


  def eitherDecoder[A, B](history: CursorHistory, f: A => String \/ B): A => DecodeResult[B] = {
    f(_).fold(x => DecodeResult.fail[B](x, history), x => DecodeResult.ok(x))
  }

  implicit def URLCodecJson: CodecJson[URL] = CodecJson(
    i => i.toString.asJson,
    i => i.as[String].flatMap(eitherDecoder(
      i.history,
      a => \/.fromTryCatchNonFatal(new URL(a)).leftMap(_ => "Could not decode URL")
    ))
  )

  implicit def UUIDCodecJson: CodecJson[UUID] = CodecJson(
    i => i.toString.asJson,
    i => i.as[String].flatMap(eitherDecoder(
      i.history,
      a => \/.fromTryCatchNonFatal(UUID.fromString(a)).leftMap(_ => "Could not decode UUID")
    ))
  )

  implicit def DurationCodecJson: CodecJson[Duration] = CodecJson(
    i => i.toMillis.asJson,
    i => i.as[Long].map(Duration.create(_, TimeUnit.MILLISECONDS))
  )

  implicit def InstantCodecJson: CodecJson[Instant] = CodecJson(
    i => i.toEpochMilli.asJson,
    i => i.as[Long].map(Instant.ofEpochMilli)
  )

  implicit def NonEmptyListDecodeJson[A: DecodeJson]: DecodeJson[NonEmptyList[A]] = {
    implicitly[DecodeJson[List[A]]].flatMap(l =>
      DecodeJson[NonEmptyList[A]](c => std.list.toNel(l) match {
        case None => DecodeResult.fail("[A]NonEmptyList[A]", c.history)
        case Some(n) => DecodeResult.ok(n)
      })
    ) setName "[A]NonEmptyList[A]"
  }

  implicit def NonEmptyListEncodeJson[A: EncodeJson]: EncodeJson[NonEmptyList[A]] =
    fromFoldable[NonEmptyList, A]

}
