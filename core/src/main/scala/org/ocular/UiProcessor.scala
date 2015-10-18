package org.ocular

import android.app.Activity
import android.content.Context
import android.support.annotation.UiThread
import android.view.View
import android.widget.{ImageView, LinearLayout, TextView}
import org.ocular.ui._
import org.ocular.utils.{Mutable, MainThreadExecutor}

class UiProcessor(activity: Activity) {
  /*unused*/ private var _viewsSack = Map[Class[_ <: View], View]
  private var _currentDef: UiComponent = _
  private var _previousContentView: Option[View] = None

  def apply(uiDef: UiComponent) = {
    MainThreadExecutor.execute(updateUi(uiDef))
  }

  @UiThread private def updateUi(uiDef: UiComponent) = new Runnable {
    override def run() = {
      updateContentViewWith(uiDefToView(uiDef, activity))
      _currentDef = uiDef
    }
  }

  @UiThread private def updateContentViewWith(view: View) = {
    if (!_previousContentView.exists(_ eq view)) {
      _previousContentView = Option(view)
      activity.setContentView(view)
    }
  }

  private def uiDefToView(uiDef: UiComponent, context: Context): View = uiDef match {
    case p @ VerticalPane(content, expand) ⇒
      Mutable.configure(new LinearLayout(context)) { view ⇒ import view._
        val layoutParams = Mutable.configure(Option(getLayoutParams.asInstanceOf[LinearLayout.LayoutParams])) { lp ⇒
          lp.width = p.width
          lp.height = p.height
        }.getOrElse(new LinearLayout.LayoutParams(p.width, p.height))
        setOrientation(LinearLayout.VERTICAL)
        setLayoutParams(layoutParams)
        content.map(uiDefToView(_, context)).foreach(addView)
      }
    case Text(string, gravity) ⇒
      Mutable.configure(new TextView(context)) { view ⇒
        gravity.map(_.value).foreach(view.setGravity)
        view.setText(string)
      }
    case Image(source) ⇒
      Mutable.configure(new ImageView(context)) { view ⇒
        view.setImageDrawable(source.fold(context.getResources.getDrawable, identity))
      }
    case other ⇒
      throw new UnsupportedOperationException("Unknown UiDef type: " + other.getClass.getName)
  }
}
