package org.ocular

class Gravity(val value: Int) extends AnyVal

object Gravity {
  def unapply(gravity: Gravity): Option[Int] = Some(gravity.value)

  lazy val TOP = new Gravity(android.view.Gravity.TOP)
  lazy val BOTTOM = new Gravity(android.view.Gravity.BOTTOM)
  lazy val LEFT = new Gravity(android.view.Gravity.LEFT)
  lazy val RIGHT = new Gravity(android.view.Gravity.RIGHT)
  lazy val CENTER = new Gravity(android.view.Gravity.CENTER)
}
