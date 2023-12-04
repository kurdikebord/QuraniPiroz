/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.quranapp.android.components.storageCleanup

import com.quranapp.android.api.models.recitation.RecitationInfoModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationInfoModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
