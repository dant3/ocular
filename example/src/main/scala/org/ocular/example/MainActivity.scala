package org.ocular.example

import android.R
import android.app.Activity
import android.util.Log
import android.view.View
import org.ocular.{Gravity, Ocular}
import org.ocular.ui._
import rx.core.Rx

class MainActivity extends Activity with Ocular[Activity] {
  val reactiveTimer: Rx[Int] = RxCounter

  override def uiDef = Rx {
    VerticalPane(
      expand = true,
      content = Seq(
        Image(R.drawable.ic_menu_today),
        Text("Counter value: " + reactiveTimer(), gravity = Gravity.CENTER)
      )
    )
  }

  override def setContentView(view: View) = {
    Log.d("OCULAR", "Content view changed")
    super.setContentView(view)
  }
}
