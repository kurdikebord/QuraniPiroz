package com.goran.quran.karim.dang.components.search

import com.goran.quran.karim.dang.components.quran.subcomponents.QuranTranslBookInfo

class VerseResultCountModel(val bookInfo: QuranTranslBookInfo?) : SearchResultModelBase() {
    @JvmField
    var resultCount = 0
}
