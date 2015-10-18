package org.ocular.example

import java.util.concurrent.{TimeUnit, Executors}

import rx.core.Var

object RxCounter extends Var[Int](0, name = "rx-counter") {
  private val updater = Executors.newSingleThreadScheduledExecutor()

  updater.scheduleAtFixedRate(new Runnable {
    override def run() = update(apply() + 1)
  }, 1, 1, TimeUnit.SECONDS)
}
