package org.ocular.utils

import android.os.{Handler, Looper}
import android.util.Log

import scala.concurrent.ExecutionContextExecutor

class LooperThreadExecutor(looper: Looper) extends ExecutionContextExecutor {
  private lazy val looperThread = looper.getThread
  private val handler = new Handler(looper)

  override def execute(runnable: Runnable) = Thread.currentThread() match {
    case `looperThread` ⇒ runnable.run()
    case _ ⇒ handler.post(runnable)
  }

  override def reportFailure(t: Throwable) = Log.e("OCULAR", "Error", t)
}

