package org.ocular.example

import java.text.{DateFormat, SimpleDateFormat}
import java.util.Date

object TimeFormatter extends ThreadLocal[DateFormat] with (Long ⇒ String) {
  override def initialValue = new SimpleDateFormat("HH:mm:ss")
  override def apply(timestamp: Long): String = get().format(new Date(timestamp))
}