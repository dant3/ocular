package org.ocular

import android.util.Log
import android.view.{ViewGroup, View}
import org.ocular.utils.ObjectSack
import org.ocular.utils.Utils._

import scala.reflect.ClassTag

class ViewCache extends ObjectSack[View] {
  def reuseAs[T <: View](view: View)(implicit classTag: ClassTag[T]): Option[T] = Option(view) match {
    case Some(nonNullView) if nonNullView.getClass == classTag.runtimeClass ⇒
      logViewReuse(nonNullView.getClass)
      Some(nonNullView.asInstanceOf[T])
    case Some(otherView) ⇒
      cacheView(otherView)
      getFromCache[T]
    case _ ⇒
      getFromCache[T]
  }

  private def getFromCache[T <: View](implicit classTag: ClassTag[T]): Option[T] = get[T] match {
    case result @ Some(view) ⇒
      logCacheHit(view.getClass)
      result
    case None ⇒
      None
  }

  def cacheView(view: View): Unit = view match {
    case null ⇒ /*noop*/
    case viewGroup: ViewGroup ⇒
      for (view ← viewGroup.childViews) cacheView(view)
      viewGroup.removeAllViews()
      put(viewGroup)
    case view: View ⇒
      put(view)
  }

  private def logViewReuse(`class`: Class[_]) = Log.d("OCULAR", "View reused: " + `class`.getName)
  private def logCacheHit(`class`: Class[_]) = Log.d("OCULAR", "View cache hit: " + `class`.getName)
}
