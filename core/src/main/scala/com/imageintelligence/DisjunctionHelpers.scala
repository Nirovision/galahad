package com.imageintelligence

import scalaz.\/

object DisjunctionHelpers {

  def splitDisjunctionList[A, B](el: List[A \/ B]): (List[A], List[B]) = {
    val (lefts, rights) = el.map(_.toEither).partition(_.isLeft)
    (lefts.map(_.left.get), rights.map(_.right.get))
  }

}
