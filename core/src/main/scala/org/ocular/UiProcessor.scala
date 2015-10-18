package org.ocular

import android.app.Activity
import android.content.Context
import android.support.annotation.UiThread
import android.view.View
import android.widget.{ImageView, LinearLayout, TextView}
import org.ocular.ui._
import org.ocular.utils.MainThreadExecutor
import org.ocular.utils.Utils._

class UiProcessor(activity: Activity) {
  private val _viewsCache = new ViewCache
  private var _currentDef: UiComponent = _
  private var _previousContentView: Option[View] = None

  def apply(uiDef: UiComponent) = {
    MainThreadExecutor.execute(updateUi(uiDef))
  }

  @UiThread private def updateUi(uiDef: UiComponent) = new Runnable {
    override def run() = {
      updateContentViewWith(uiDefToView(uiDef, _previousContentView.orNull, activity))
      _currentDef = uiDef
    }
  }

  @UiThread private def updateContentViewWith(view: View) = {
    if (!_previousContentView.exists(_ eq view)) {
      _previousContentView = Option(view)
      activity.setContentView(view)
    }
  }

  private def uiDefToView(uiDef: UiComponent, previousView: View, context: Context): View = uiDef match {
    case p @ VerticalPane(content, expand) ⇒
      val linearLayout = _viewsCache.reuseAs[LinearLayout](previousView).getOrElse(new LinearLayout(context))
      configure(linearLayout) { view ⇒ import view._
        val layoutParams = configure(Option(getLayoutParams)) { lp ⇒
          lp.width = p.width
          lp.height = p.height
        }.getOrElse(new LinearLayout.LayoutParams(p.width, p.height))
        setOrientation(LinearLayout.VERTICAL)
        setLayoutParams(layoutParams)

        val reusableChilds = (view.childViews.toStream #::: Stream.continually[View](null)).zipWithIndex
        content zip reusableChilds foreach {
          case (childUiDef, (childView, index)) ⇒
            val newView = uiDefToView(childUiDef, childView, context)
            if (!(childView eq newView)) {
              if (childView != null) {
                removeViewAt(index)
              }
              addView(newView, index)
            }
        }
      }
    case Text(string, gravity) ⇒
      val textView = _viewsCache.reuseAs[TextView](previousView).getOrElse(new TextView(context))
      configure(textView) { view ⇒
        gravity.map(_.value).foreach(view.setGravity)
        view.setText(string)
      }
    case Image(source) ⇒
      val imageView = _viewsCache.reuseAs[ImageView](previousView).getOrElse(new ImageView(context))
      configure(imageView) { view ⇒
        view.setImageDrawable(source.fold(context.getResources.getDrawable, identity))
      }
    case other ⇒
      throw new UnsupportedOperationException("Unknown UiComponent type: " + other.getClass.getName)
  }
}
