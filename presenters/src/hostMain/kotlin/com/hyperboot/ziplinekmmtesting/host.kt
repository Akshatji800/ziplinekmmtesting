package com.hyperboot.ziplinekmmtesting

import app.cash.zipline.Zipline
import app.cash.zipline.loader.LoadResult
import app.cash.zipline.loader.ZiplineLoader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun startWorldClockZipline(
  scope: CoroutineScope,
  ziplineDispatcher: CoroutineDispatcher,
  ziplineLoader: ZiplineLoader,
  manifestUrl: String,
  host: WorldClockHost,
  events: Flow<WorldClockEvent>,
  models: MutableStateFlow<WorldClockModel>,
) {
  scope.launch(ziplineDispatcher + SupervisorJob()) {
    val loadResultFlow: Flow<LoadResult> = ziplineLoader.load(
      applicationName = "world-clock",
      manifestUrlFlow = repeatFlow(manifestUrl, 500L),
      initializer = { zipline: Zipline ->
        zipline.bind("WorldClockHost", host)
      },
    )

    var previousJob: Job? = null

    loadResultFlow.collect { result ->
      previousJob?.cancel()
      println("zzz  $result")

      if (result is LoadResult.Success) {
        val zipline = result.zipline
        val presenter = zipline.take<WorldClockPresenter>("WorldClockPresenter")

        val job = launch {
          models.emitAll(presenter.models(events))
        }

        job.invokeOnCompletion {
          presenter.close()
          // TODO(jwilson): make this safe.
          // zipline.close()
        }

        previousJob = job
      }
    }
  }
}

/** Poll for code updates by emitting the manifest on an interval. */
private fun <T> repeatFlow(content: T, delayMillis: Long): Flow<T> {
  return flow {
    while (true) {
      emit(content)
      delay(delayMillis)
    }
  }
}
