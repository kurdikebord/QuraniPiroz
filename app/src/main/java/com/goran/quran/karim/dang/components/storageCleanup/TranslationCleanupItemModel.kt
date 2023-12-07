/*
 * Created by Faisal Khan on (c) 16/8/2021.
 */
package com.goran.quran.karim.dang.components.storageCleanup

import com.goran.quran.karim.dang.components.quran.subcomponents.QuranTranslBookInfo
import com.goran.quran.karim.dang.components.transls.TranslBaseModel

class TranslationCleanupItemModel(val bookInfo: QuranTranslBookInfo) : TranslBaseModel() {
    var isDeleted: Boolean = false
}
