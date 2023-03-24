package com.goran.quranipiroz.vh.search;

import com.goran.quranipiroz.components.search.ChapterJumpModel;
import com.goran.quranipiroz.components.search.SearchResultModelBase;
import com.goran.quranipiroz.utils.reader.factory.ReaderFactory;
import com.goran.quranipiroz.widgets.chapterCard.ChapterCard;

public class VHChapterJump extends VHSearchResultBase {
    private final ChapterCard mChapterCard;

    public VHChapterJump(ChapterCard chapterCard, boolean applyMargins) {
        super(chapterCard);
        mChapterCard = chapterCard;

        setupJumperView(chapterCard, applyMargins);
    }


    @Override
    public void bind(SearchResultModelBase parentModel, int pos) {
        ChapterJumpModel model = (ChapterJumpModel) parentModel;

        mChapterCard.setChapterNumber(model.getChapterNo());
        mChapterCard.setName(model.getName(), model.getNameTranslation());
        mChapterCard.setOnClickListener(v -> ReaderFactory.startChapter(v.getContext(), model.getChapterNo()));
    }
}
