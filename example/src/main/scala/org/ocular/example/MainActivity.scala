package org.ocular.example

import android.R
import android.app.Activity
import org.ocular._
import rx.core.Rx

class MainActivity extends Activity with Ocular[Activity] {
  override def uiDef = Rx {
    VerticalPane(
      expand = true,
      content = Seq(
        Image(R.drawable.ic_menu_today),
        Text("Counter: " + RxCounter())
      )
    )
  }
}
