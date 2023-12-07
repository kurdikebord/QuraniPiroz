/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quran.karim.dang.components.storageCleanup

import com.goran.quran.karim.dang.api.models.tafsir.TafsirInfoModel

data class TafsirCleanupItemModel(
    val tafsirModel: TafsirInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
