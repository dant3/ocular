package org.ocular

import android.app.Activity
import android.view.View
import org.ocular.ui.UiComponent
import rx.core.Rx

trait Ocular[T <: Activity] extends Activity { self: T ⇒
  private val uiProcessor = new UiProcessor(this)
  private var uiDefBinding: Option[Rx[Unit]] = None

  def uiDef: Rx[UiComponent[_ <: View]]

  override def onResume(): Unit = {
    super.onResume()
    uiDefBinding = Some(Rx {
      uiProcessor(uiDef())
    })
  }

  override def onPause() = {
    uiDefBinding.foreach(_.kill())
    uiDefBinding = None
    super.onPause()
  }
}
