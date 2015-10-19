package org.ocular.ui

import android.content.Context
import android.support.annotation.UiThread
import android.view.View

import scala.reflect.ClassTag

trait UiComponent[T <: View] {
  def classTag: ClassTag[T]

  /**
   * create a new view what can be binded by this component later
   * @param context
   * @return
   */
  @UiThread def createView(context: Context): T

  /**
   * Bind configuration of this UiComponent to the view given particular android Context.
   * The returned sequence contains child views that are no more required (if applicable).
   * Those views will be reused by system to improve overall ui performance
   * @param view
   * @param context
   * @return
   */
  @UiThread def bindView(view: T, context: Context): Seq[View]
}




