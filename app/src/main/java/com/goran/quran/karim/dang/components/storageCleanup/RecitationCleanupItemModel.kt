/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quran.karim.dang.components.storageCleanup

import com.goran.quran.karim.dang.api.models.recitation.RecitationInfoModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
