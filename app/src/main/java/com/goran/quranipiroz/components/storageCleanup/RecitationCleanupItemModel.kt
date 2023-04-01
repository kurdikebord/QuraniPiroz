package com.goran.quranipiroz.components.storageCleanup

import com.goran.quranipiroz.api.models.recitation.RecitationInfoModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
