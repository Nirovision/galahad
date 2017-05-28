package com.imageintelligence.galahad.argonaut

import java.net.URL
import java.time.Instant
import java.util.UUID

import argonaut._
import ArgonautHelpers._
import com.imageintelligence.galahad.core.Generators._
import org.scalacheck._

import scala.concurrent.duration.Duration
import scalaz.NonEmptyList

class ArgonautHelpersSpec extends Properties("ArgonautHelpers") {

  property("URL encode/decode") = encodeDecodeLaw[URL]

  property("UUID encode/decode") = encodeDecodeLaw[UUID]

  property("Duration encode/decode") = encodeDecodeLaw[Duration]

  property("Instant encode/decode") = encodeDecodeLaw[Instant]

  property("NonEmptyList[Int] encode/decode") = encodeDecodeLaw[NonEmptyList[Int]]

  property("NonEmptyList[String] encode/decode") = encodeDecodeLaw[NonEmptyList[String]]
}
