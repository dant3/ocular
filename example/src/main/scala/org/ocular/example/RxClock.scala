package org.ocular.example

import java.util.concurrent.{TimeUnit, Executors}

import rx.core.Var

object RxClock extends Var[Long](System.currentTimeMillis(), name = "rx-clock") {
  private val updater = Executors.newSingleThreadScheduledExecutor()

  updater.scheduleAtFixedRate(new Runnable {
    override def run() = update(System.currentTimeMillis())
  }, 1, 1, TimeUnit.SECONDS)
}
