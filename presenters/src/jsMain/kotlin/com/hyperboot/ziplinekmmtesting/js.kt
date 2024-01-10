package com.hyperboot.ziplinekmmtesting

import app.cash.zipline.Zipline
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private val zipline by lazy { Zipline.get() }

@OptIn(ExperimentalJsExport::class)
@JsExport
fun main() {
  val worldClockHost = zipline.take<WorldClockHost>("WorldClockHost")
  zipline.bind<WorldClockPresenter>(
    name = "WorldClockPresenter",
    instance = RealWorldClockPresenter(worldClockHost),
  )
}

class RealWorldClockPresenter(
  private val host: WorldClockHost,
) : WorldClockPresenter {
  override fun models(
    events: Flow<WorldClockEvent>,
  ): Flow<WorldClockModel> {
    return flow {
      while (true) {
        emit(
          WorldClockModel(
            label = TimeFormatter().formatLocalTime(millis = true)
          ),
        )
        delay(16)
      }
    }
  }
}
