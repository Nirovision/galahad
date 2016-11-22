package com.ii.galahad.argonaut

import java.net.URL
import java.util.UUID

import argonaut._
import org.scalacheck._
import com.ii.galahad.argonaut.ArgonautHelpers._
import com.ii.galahad.core.Generators._
import org.scalatest.PropSpec
import org.scalatest.prop.Checkers

import scala.concurrent.duration.Duration

class ArgonautHelpersSpec extends PropSpec with Checkers {

  def encodeDecodeLaw[A: DecodeJson : EncodeJson : Arbitrary]: Prop = {
    org.scalacheck.Prop.forAll { a: A =>
      implicitly[DecodeJson[A]].apply(implicitly[EncodeJson[A]].apply(a).hcursor).value exists (_ === a)
    }
  }

  property("URL encode/decode") {
    check(encodeDecodeLaw[URL], MinSuccessful(100))
  }

  property("UUID encode/decode") {
    check(encodeDecodeLaw[UUID], MinSuccessful(100))
  }

  property("Duration encode/decode") {
    check(encodeDecodeLaw[Duration], MinSuccessful(100))
  }



}
