package org.ocular.utils

import android.util.Log

trait Logger {
  def log(string: String) = if (Logger.enabled) Log.d("OCULAR", string)
}

object Logger {
  val enabled = false
}
