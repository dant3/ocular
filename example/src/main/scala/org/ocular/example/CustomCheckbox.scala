package org.ocular.example

import android.content.Context
import android.support.annotation.UiThread
import android.widget.{CompoundButton, Switch}
import org.ocular.ui.{UiComponent, ViewConfigurator}

case class CustomCheckbox(checked: Boolean,
                          onCheck: (Boolean) ⇒ Any = null,
                          hint: CharSequence = null) extends ViewConfigurator[Switch]
                                                                      with UiComponent[Switch] {
  @UiThread override def createView(context: Context) = new Switch(context)

  @UiThread override def bindView(view: Switch, context: Context) = {
    view.setChecked(checked)
    view.setOnCheckedChangeListener(
      Option(onCheck).map(fn ⇒ new CompoundButton.OnCheckedChangeListener {
        override def onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean): Unit = {
          fn(isChecked)
        }
      }).orNull
    )
    view.setText(hint)
    Seq.empty
  }
}
