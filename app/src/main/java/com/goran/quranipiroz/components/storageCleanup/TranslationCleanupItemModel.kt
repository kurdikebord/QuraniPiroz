/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quranipiroz.components.storageCleanup

import com.goran.quranipiroz.components.quran.subcomponents.QuranTranslBookInfo
import com.goran.quranipiroz.components.transls.TranslBaseModel

class TranslationCleanupItemModel(val bookInfo: QuranTranslBookInfo) : TranslBaseModel() {
    var isDeleted: Boolean = false
}
