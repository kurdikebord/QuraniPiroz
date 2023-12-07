package com.goran.quran.karim.dang.vh.search

import com.goran.quran.karim.dang.components.search.ChapterJumpModel
import com.goran.quran.karim.dang.components.search.SearchResultModelBase
import com.goran.quran.karim.dang.utils.reader.factory.ReaderFactory.startChapter
import com.goran.quran.karim.dang.widgets.chapterCard.ChapterCard

class VHChapterJump(private val chapterCard: ChapterCard, applyMargins: Boolean) : VHSearchResultBase(chapterCard) {
    init {
        setupJumperView(chapterCard, applyMargins)
    }

    override fun bind(model: SearchResultModelBase, pos: Int) {
        if (model is ChapterJumpModel) {
            chapterCard.apply {
                chapterNumber = model.chapterNo
                setName(model.name, model.nameTranslation)
                setOnClickListener { startChapter(it.context, model.chapterNo) }
            }
        }
    }
}