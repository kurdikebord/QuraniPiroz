package com.goran.quranipiroz.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppUpdate(
    val version: Long,
    @SerialName("updatePriority") val priority: Int
)
