package com.goran.quranipiroz.components.search

import com.goran.quranipiroz.components.quran.subcomponents.QuranTranslBookInfo

class VerseResultCountModel(val bookInfo: QuranTranslBookInfo?) : SearchResultModelBase() {
    @JvmField
    var resultCount = 0
}
