package com.goran.quranipiroz.components.storageCleanup

import com.goran.quranipiroz.api.models.tafsir.TafsirInfoModel

data class TafsirCleanupItemModel(
    val tafsirModel: TafsirInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
