package com.goran.quranipiroz.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppUrls(
    @SerialName("kurdikeyboard") val kurdiKey: String,
    val about: String,
    val help: String,
    val feedback: String,
    val facebook: String
)
