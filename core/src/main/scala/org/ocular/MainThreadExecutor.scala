package org.ocular

import android.os.{Handler, Looper}
import android.util.Log

import scala.concurrent.ExecutionContextExecutor

object MainThreadExecutor extends ExecutionContextExecutor {
  private val looper = Looper.getMainLooper
  private lazy val mainThread = looper.getThread
  private val handler = new Handler(looper)

  override def execute(runnable: Runnable) = Thread.currentThread() match {
    case `mainThread` ⇒ runnable.run()
    case _ ⇒ handler.post(runnable)
  }

  override def reportFailure(t: Throwable) = Log.e("OCULAR", "Error", t)
}
