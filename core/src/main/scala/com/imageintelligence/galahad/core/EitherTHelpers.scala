package com.imageintelligence.galahad.core

import scalaz._
import Scalaz._

object EitherTHelpers {

  implicit class EitherTPimp[F[_]: Monad, E, A](e: EitherT[F, E, A]) {
    def bindIgnoring[O, B](e2: EitherT[F, O, B]): EitherT[F, O, B] = {
      EitherT.right(e.run).flatMap(_ => e2)
    }

    def foldE[X, O, B](l: E => EitherT[F, O, B], r: A => EitherT[F, O, B]): EitherT[F, O, B] = {
      EitherT(e.run.flatMap {
        case \/-(r1) => r(r1).run
        case -\/(l1) => l(l1).run
      })
    }
  }
}
