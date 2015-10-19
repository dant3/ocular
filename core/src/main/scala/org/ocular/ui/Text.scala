package org.ocular.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import org.ocular.Gravity

case class Text(string: CharSequence, gravity: Option[Gravity] = None) extends ViewConfigurator[TextView]
                                                                               with UiComponent[TextView] {
  override def createView(context: Context) = new TextView(context)
  override def bindView(view: TextView, context: Context): Seq[View] = {
    gravity.map(_.value).foreach(view.setGravity)
    view.setText(string)
    Seq.empty
  }
}

object Text {
  def apply(string: CharSequence, gravity: Gravity): Text = Text(string, Some(gravity))
}