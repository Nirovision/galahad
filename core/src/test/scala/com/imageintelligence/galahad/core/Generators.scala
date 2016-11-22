package com.imageintelligence.galahad.core

import java.net.URL
import java.util.UUID
import java.util.concurrent.TimeUnit

import org.scalacheck._

import scala.concurrent.duration.Duration


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

  val genDuration: Gen[Duration] =
    Arbitrary.arbInt.arbitrary.map(i => Duration.create(i.toLong, TimeUnit.MILLISECONDS))

  implicit val arbURL: Arbitrary[URL] = Arbitrary(genURL)
  implicit val arbDuration: Arbitrary[Duration] = Arbitrary(genDuration)
  implicit val arbUUID: Arbitrary[UUID] = Arbitrary(Gen.uuid)


}
