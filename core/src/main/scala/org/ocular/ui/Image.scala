package org.ocular.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView

import scala.util.{Either, Left, Right}

case class Image private(source: Either[Int, Drawable]) extends ViewConfigurator[ImageView] with UiComponent[ImageView] {
  override def createView(context: Context) = new ImageView(context)
  override def bindView(view: ImageView, context: Context): Seq[View] = {
    view.setImageDrawable(source.fold(context.getResources.getDrawable, identity))
    Seq.empty
  }
}

object Image {
  def apply(@DrawableRes imageRes: Int): Image = Image(Left(imageRes))
  def apply(drawable: Drawable): Image = Image(Right(drawable))
}
