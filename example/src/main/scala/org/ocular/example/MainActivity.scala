package org.ocular.example

import android.app.Activity
import android.util.Log
import android.view.View
import org.ocular.ui._
import org.ocular.{Gravity, Ocular}
import rx.core.{Var, Rx}

class MainActivity extends Activity with Ocular[Activity] {
  val checkBoxChecked = Var(false)
  val currentTime: Rx[String] = Rx { TimeFormatter(RxClock()) }

  override def uiDef = Rx {
    VerticalPane(
      expand = true,
      content = Seq(
        Image(android.R.drawable.ic_menu_today),
        Text("Time: " + currentTime(), gravity = Gravity.CENTER),
        CustomCheckbox(
          checked = checkBoxChecked(),
          onCheck = checkBoxChecked.update(_),
          hint = s"Custom checkbox checked: ${checkBoxChecked()}"
        )
      )
    )
  }

  override def setContentView(view: View) = {
    Log.d("OCULAR", "Content view changed")
    super.setContentView(view)
  }
}
