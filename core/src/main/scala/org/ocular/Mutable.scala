package org.ocular

object Mutable {
  def configure[T](mutableValue: T)(configurator: T ⇒ Any) : T = {
    configurator(mutableValue)
    mutableValue
  }

  def configure[T](mutableValue: Option[T])(configurator: T ⇒ Any) : Option[T] =
    mutableValue.map(configure(_)(configurator))
}
