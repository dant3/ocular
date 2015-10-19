package org.ocular.ui

import android.content.Context
import android.support.annotation.UiThread
import android.view.{View, ViewGroup}
import android.widget.LinearLayout
import org.ocular.utils.Logger
import org.ocular.utils.Utils._

case class VerticalPane(content: Seq[UiComponent[_ <: View]], expand: Boolean = false) extends ViewConfigurator[LinearLayout]
                                                                                    with UiComponent[LinearLayout]
                                                                                    with Logger {
  def width = ViewGroup.LayoutParams.MATCH_PARENT
  def height = if (expand) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT

  @UiThread override def createView(context: Context) = new LinearLayout(context)
  @UiThread override def bindView(view: LinearLayout, context: Context): Seq[View] = {
    val layoutParams = configure(Option(view.getLayoutParams)) { lp ⇒
      lp.width = width
      lp.height = height
    }.getOrElse(new LinearLayout.LayoutParams(width, height))
    view.setOrientation(LinearLayout.VERTICAL)
    view.setLayoutParams(layoutParams)

    val reusableChilds = (view.childViews.toStream #::: Stream.continually[View](null)).zipWithIndex
    val spareViews = content zip reusableChilds map {
      case (childUi, (childView, index)) ⇒
        val newView = processComponentToView(childUi, childView, context)
        def viewReused = childView eq newView
        if (viewReused) {
          log("Old child view reused for " + newView.getClass.getName)
          None
        } else {
          if (childView != null) {
            log("Old view in hierarchy removed at " + index)
            view.removeViewAt(index)
          }
          log("New view in hierarchy added to " + index)
          view.addView(newView, index)
          Option(childView)
        }
    }
    spareViews.filter(_.isDefined).map(_.get)
  }
}