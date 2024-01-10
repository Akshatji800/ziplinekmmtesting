package com.hyperboot.ziplinekmmtesting


import app.cash.zipline.ZiplineService
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class WorldClockEvent(
    val event: String,
)

@Serializable
data class WorldClockModel(
    val label: String,
)

interface WorldClockPresenter : ZiplineService {
    fun models(events: Flow<WorldClockEvent>): Flow<WorldClockModel>
}

interface WorldClockHost : ZiplineService {
    fun timeZones(): List<String>
}