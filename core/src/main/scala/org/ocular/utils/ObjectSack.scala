package org.ocular.utils

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.util.Try

class ObjectSack[T] {
  private val content = mutable.Map.empty[Class[_ <: T], mutable.Queue[T]]
  
  def getOrCreate[U <: T](creator: ⇒ U)(implicit classTag: ClassTag[U]): U = get(classTag).getOrElse(creator)
  def getOrCreate[U <: T](objectClass: Class[U], creator: ⇒ U): U = get(objectClass).getOrElse(creator)

  def get[U <: T](implicit classTag: ClassTag[U]): Option[U] = get(classTag.runtimeClass.asInstanceOf[Class[U]])
  def get[U <: T](objectClass: Class[U]): Option[U] = content.get(objectClass) match {
    case Some(objectsCache) ⇒ Try(objectsCache.dequeue().asInstanceOf[U]).toOption
    case None ⇒ None
  }

  def put[U <: T](`object`: U): Unit = {
    val objectClass = `object`.getClass
    content.get(objectClass) match {
      case Some(objectsCache) ⇒ objectsCache.enqueue(`object`)
      case None ⇒ content.put(objectClass, mutable.Queue(`object`))
    }
  }
}
