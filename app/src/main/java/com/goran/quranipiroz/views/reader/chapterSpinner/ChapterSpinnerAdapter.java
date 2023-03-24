package com.goran.quranipiroz.views.reader.chapterSpinner;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.goran.quranipiroz.components.quran.subcomponents.Chapter;
import com.goran.quranipiroz.views.reader.spinner.ReaderSpinnerAdapter;
import com.goran.quranipiroz.views.reader.spinner.ReaderSpinnerItem;
import com.goran.quranipiroz.widgets.chapterCard.ChapterCard;

import java.util.List;

public class ChapterSpinnerAdapter extends ReaderSpinnerAdapter<ChapterSpinnerAdapter.VHChapterSpinner> {
    public ChapterSpinnerAdapter(Context ctx, List<ReaderSpinnerItem> items) {
        super(ctx, items);
    }

    @Override
    public ChapterSpinnerItem getItem(int position) {
        return (ChapterSpinnerItem) super.getItem(position);
    }

    @Override
    public ChapterSpinnerItem getItemFromStoredList(int position) {
        return (ChapterSpinnerItem) super.getItemFromStoredList(position);
    }

    @Override
    public ChapterSpinnerItem getSelectedItem() {
        return (ChapterSpinnerItem) super.getSelectedItem();
    }

    @NonNull
    @Override
    public ChapterSpinnerAdapter.VHChapterSpinner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChapterCard chapterCard = new ChapterCard(parent.getContext());
        return new ChapterSpinnerAdapter.VHChapterSpinner(chapterCard);
    }

    public class VHChapterSpinner extends VHReaderSpinner {
        private final ChapterCard mChapterCard;

        public VHChapterSpinner(ChapterCard chapterCard) {
            super(ChapterSpinnerAdapter.this, chapterCard);
            mChapterCard = chapterCard;
        }

        @Override
        public void bind(ReaderSpinnerItem item) {
            super.bind(item);
            Chapter chapter = ((ChapterSpinnerItem) item).getChapter();
            mChapterCard.setChapterNumber(chapter.getChapterNumber());
            mChapterCard.setName(chapter.getName(), chapter.getNameTranslation());
        }
    }
}
