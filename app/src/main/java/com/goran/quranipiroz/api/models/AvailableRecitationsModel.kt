package com.goran.quranipiroz.api.models

import com.goran.quranipiroz.components.recitation.RecitationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableRecitationsModel(
    @SerialName("url-info") val urlInfo: RecitationsCommonUrlInfoModel,
    @SerialName("reciters") val reciters: List<RecitationModel>
)
