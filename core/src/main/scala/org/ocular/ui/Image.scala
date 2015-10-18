package org.ocular.ui

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import org.ocular.ui.UiComponent

import scala.util.{Either, Left, Right}

case class Image private(source: Either[Int, Drawable]) extends UiComponent

object Image {
  def apply(@DrawableRes imageRes: Int): Image = Image(Left(imageRes))
  def apply(drawable: Drawable): Image = Image(Right(drawable))
}
