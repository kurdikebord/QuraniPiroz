package com.goran.quranipiroz.api.models.tafsir

import com.goran.quranipiroz.api.models.tafsir.TafsirInfoModel
import kotlinx.serialization.Serializable

@Serializable
data class AvailableTafsirsModel(
    val tafsirs: Map<String, List<TafsirInfoModel>>
)
