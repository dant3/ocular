package org.ocular

import android.app.Activity
import android.content.Context
import android.view.{View, ViewGroup}
import android.widget.{ImageView, LinearLayout, TextView}

class UiProcessor(activity: Activity) {
  /*unused*/ private var _viewsSack = None
  private var _currentDef: UiDef = _

  def apply(uiDef: UiDef) = {
    MainThreadExecutor.execute(updateUi(uiDef))
  }

  private def updateUi(uiDef: UiDef) = new Runnable {
    override def run() = {
      activity.setContentView(uiDefToView(uiDef, activity))
      _currentDef = uiDef
    }
  }

  private def uiDefToView(uiDef: UiDef, context: Context): View = uiDef match {
    case VerticalPane(content, expand) ⇒
      Mutable.configure(new LinearLayout(context)) { view ⇒ import view._
        Mutable.configure(Option(getLayoutParams.asInstanceOf[LinearLayout.LayoutParams])) { lp ⇒
          lp.width = ViewGroup.LayoutParams.MATCH_PARENT
          lp.height = if (expand) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        }.foreach(setLayoutParams)
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
