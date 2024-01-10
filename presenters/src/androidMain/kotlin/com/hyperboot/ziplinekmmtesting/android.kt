package com.hyperboot.ziplinekmmtesting

import android.content.Context
import app.cash.zipline.loader.ManifestVerifier.Companion.NO_SIGNATURE_CHECKS
import app.cash.zipline.loader.ZiplineLoader
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import okhttp3.OkHttpClient

class WorldClockAndroid(
    private val applicationContext: Context,
    private val scope: CoroutineScope,
) {
    private val ziplineExecutorService = Executors.newSingleThreadExecutor { Thread(it, "Zipline") }
    private val ziplineDispatcher = ziplineExecutorService.asCoroutineDispatcher()
    private val okHttpClient = OkHttpClient()

    val events = flowOf<WorldClockEvent>()
    val models = MutableStateFlow(WorldClockModel(label = "..."))

    fun start() {
        startWorldClockZipline(
            scope = scope,
            ziplineDispatcher = ziplineDispatcher,
            ziplineLoader = ZiplineLoader(
                dispatcher = ziplineDispatcher,
                manifestVerifier = NO_SIGNATURE_CHECKS,
                httpClient = okHttpClient,
            ),
            manifestUrl = "http://10.0.2.2:8080/manifest.zipline.json",
            host = RealWorldClockHost(),
            events = events,
            models = models,
        )
    }

    fun close() {
        ziplineExecutorService.shutdown()
    }
}
