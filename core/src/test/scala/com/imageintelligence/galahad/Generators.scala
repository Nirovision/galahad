package com.imageintelligence.galahad.core

import java.net.URL
import java.time.Instant
import java.util.UUID
import java.util.concurrent.TimeUnit

import org.scalacheck._

import scala.concurrent.duration.Duration
import scalaz._
import Scalaz._


object Generators {

  val genPathElement: Gen[String] = for {
    length <- Gen.choose(1, 10)
    path   <- Gen.listOfN(length, Gen.alphaLowerChar)
  } yield path.mkString

  val genURL: Gen[URL] = for {
    protocol <- Gen.oneOf("http", "https")
    host     <- Gen.identifier
    port     <- Gen.choose(0, 65535)
    length   <- Gen.choose(0, 5)
    path     <- Gen.listOfN(length, genPathElement)
  } yield new URL(protocol, host, port, path.mkString("/", "/", ""))

  def genNel[A](g: Gen[A]): Gen[NonEmptyList[A]] = {
    Gen.nonEmptyListOf(g).map(l => NonEmptyList.nel(l.head, l.tail.toIList))
  }

  val genDuration: Gen[Duration] =
    Arbitrary.arbInt.arbitrary.map(i => Duration.create(i.toLong, TimeUnit.MILLISECONDS))

  val genInstant: Gen[Instant] =
    Arbitrary.arbLong.arbitrary.map(Instant.ofEpochMilli)

  implicit val arbURL: Arbitrary[URL] = Arbitrary(genURL)
  implicit val arbDuration: Arbitrary[Duration] = Arbitrary(genDuration)
  implicit val arbUUID: Arbitrary[UUID] = Arbitrary(Gen.uuid)
  implicit val arbInstant: Arbitrary[Instant] = Arbitrary(genInstant)
  implicit val arbNelInt: Arbitrary[NonEmptyList[Int]] = Arbitrary(genNel(Gen.choose(Int.MinValue, Int.MaxValue)))
  implicit val arbNelString: Arbitrary[NonEmptyList[String]] = Arbitrary(genNel(Gen.alphaStr))
}
