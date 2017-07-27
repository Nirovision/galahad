package com.imageintelligence

import scalaz.Monad
import scalaz.EitherT

object EitherTHelpers {

  implicit class EitherTPimp[F[_]: Monad, E, A](e: EitherT[F, E, A]) {
    def bindIgnoring[O, B](e2: EitherT[F, O, B]): EitherT[F, O, B] = {
      EitherT.right(e.run).flatMap(_ => e2)
    }
  }

}
