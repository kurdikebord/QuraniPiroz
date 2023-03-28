package com.goran.quranipiroz.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppUrls(
    @SerialName("kurdikeyboard") val privacyPolicy: String,
    val about: String,
    val help: String,
    val feedback: String
)
