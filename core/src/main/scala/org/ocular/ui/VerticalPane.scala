package org.ocular.ui

import android.view.ViewGroup

case class VerticalPane(content: Seq[UiComponent], expand: Boolean = false) extends UiComponent {
  def width = ViewGroup.LayoutParams.MATCH_PARENT
  def height = if (expand) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
}