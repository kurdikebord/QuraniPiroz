package com.goran.quran.karim.dang.components

import com.goran.quran.karim.dang.components.quran.subcomponents.Verse

class ReferenceVerseItemModel(
    val viewType: Int,
    val verse: Verse?,
    val chapterNo: Int,
    val fromVerse: Int,
    val toVerse: Int,
    val titleText: String?,
    var bookmarked: Boolean,
)
