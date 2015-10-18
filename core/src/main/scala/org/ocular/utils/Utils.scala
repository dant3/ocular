package org.ocular.utils

import android.view.ViewGroup

object Utils {
  def configure[T](mutableValue: T)(configurator: T ⇒ Any) : T = {
    configurator(mutableValue)
    mutableValue
  }

  def configure[T](mutableValue: Option[T])(configurator: T ⇒ Any) : Option[T] =
    mutableValue.map(configure(_)(configurator))

  implicit class PimpedViewGroup(viewGroup: ViewGroup) {
    def childViews = for (i ← 0 until viewGroup.getChildCount) yield viewGroup.getChildAt(i)
  }
}
