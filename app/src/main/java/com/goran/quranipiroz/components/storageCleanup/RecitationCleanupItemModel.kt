package com.goran.quranipiroz.components.storageCleanup

import com.goran.quranipiroz.api.models.recitation.RecitationModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
