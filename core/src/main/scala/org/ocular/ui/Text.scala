package org.ocular.ui

import org.ocular.Gravity

case class Text(string: CharSequence, gravity: Option[Gravity] = None) extends UiComponent

object Text {
  def apply(string: CharSequence, gravity: Gravity): Text = Text(string, Some(gravity))
}