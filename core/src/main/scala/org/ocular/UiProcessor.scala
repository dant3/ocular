package org.ocular

import android.app.Activity
import android.content.Context
import android.support.annotation.UiThread
import android.view.View
import org.ocular.ui._
import org.ocular.utils.MainThreadExecutor

class UiProcessor(activity: Activity) {
  private var _currentDef: UiComponent[_] = _
  private var _previousContentView: Option[View] = None

  def apply(uiDef: UiComponent[_ <: View]) = {
    MainThreadExecutor.execute(updateUi(uiDef))
  }

  @UiThread private def updateUi(uiDef: UiComponent[_ <: View]) = new Runnable {
    override def run() = {
      updateContentViewWith(UiProcessor.processComponentToView(uiDef, _previousContentView.orNull, activity))
      _currentDef = uiDef
    }
  }

  @UiThread private def updateContentViewWith(view: View) = {
    if (!_previousContentView.exists(_ eq view)) {
      _previousContentView = Option(view)
      activity.setContentView(view)
    }
  }
}

object UiProcessor {
  private val _viewsCache = new ViewCache

  @UiThread def processComponentToView[T <: View](ui: UiComponent[T], previousView: View, context: Context): T = {
    val componentView = _viewsCache.reuseAs(previousView)(ui.classTag).getOrElse(ui.createView(context))
    ui.bindView(componentView, context).foreach(_viewsCache.cacheView)
    componentView
  }
}
