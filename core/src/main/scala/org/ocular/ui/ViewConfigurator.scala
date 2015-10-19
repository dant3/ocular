package org.ocular.ui

import android.content.Context
import android.support.annotation.UiThread
import android.view.View
import org.ocular.UiProcessor

import scala.reflect.ClassTag

class ViewConfigurator[T <: View](implicit val classTag: ClassTag[T]) {
  @UiThread def processComponentToView[U <: View](ui: UiComponent[U], previousView: View, context: Context): U = {
    UiProcessor.processComponentToView(ui, previousView, context)
  }
}
