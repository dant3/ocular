package org.ocular

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes

import scala.util.{Either, Left, Right}

sealed trait UiDef

case class Image private(source: Either[Int, Drawable]) extends UiDef
object Image {
  def apply(@DrawableRes imageRes: Int): Image = Image(Left(imageRes))
  def apply(drawable: Drawable): Image = Image(Right(drawable))
}

case class Text(string: CharSequence, gravity: Option[Text.Gravity] = None) extends UiDef

object Text {
  def apply(string: CharSequence, gravity: Gravity): Text = Text(string, Some(gravity))

  class Gravity(val value: Int) extends AnyVal
  object Gravity {
    def unapply(gravity: Gravity): Option[Int] = Some(gravity.value)

    lazy val TOP = new Gravity(android.view.Gravity.TOP)
    lazy val BOTTOM = new Gravity(android.view.Gravity.BOTTOM)
    lazy val LEFT = new Gravity(android.view.Gravity.LEFT)
    lazy val RIGHT = new Gravity(android.view.Gravity.RIGHT)
    lazy val CENTER = new Gravity(android.view.Gravity.CENTER)
  }
}

case class VerticalPane(content: Seq[UiDef], expand: Boolean = false) extends UiDef