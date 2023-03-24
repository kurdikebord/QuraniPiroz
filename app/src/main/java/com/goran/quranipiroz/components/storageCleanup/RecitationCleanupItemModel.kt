/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quranipiroz.components.storageCleanup

import com.goran.quranipiroz.components.recitation.RecitationModel

data class RecitationCleanupItemModel(
    val recitationModel: RecitationModel,
    val downloadsCount: Int,
    var isCleared: Boolean = false
)
