/*
 * (c) Faisal Khan. Created on 2/2/2022.
 */

package com.goran.quran.karim.dang.adapters.quranIndex;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.MarginLayoutParams;

import com.peacedesign.android.utils.Dimen;
import com.goran.quran.karim.dang.R;
import com.goran.quran.karim.dang.components.quran.QuranMeta;
import com.goran.quran.karim.dang.frags.readerindex.BaseFragReaderIndex;
import com.goran.quran.karim.dang.utils.extensions.LayoutParamsKt;
import com.goran.quran.karim.dang.utils.reader.factory.ReaderFactory;
import com.goran.quran.karim.dang.viewModels.FavChaptersViewModel;
import com.goran.quran.karim.dang.widgets.chapterCard.ChapterCard;

import java.util.ArrayList;
import java.util.List;

public class ADPChaptersList extends ADPReaderIndexBase<ADPChaptersList.VHChapter> {
    private List<Integer> mChapterNos = new ArrayList<>();

    public ADPChaptersList(BaseFragReaderIndex fragment, Context ctx, boolean reverse) {
        super(fragment, reverse);

        initADP(ctx);
    }

    @Override
    protected void prepareList(Context ctx, boolean reverse) {
        mChapterNos = new ArrayList<>();

        int from = reverse ? QuranMeta.totalChapters() : 1;
        int to = reverse ? 1 : QuranMeta.totalChapters();
        int chapterNo = from;

        while (true) {
            mChapterNos.add(chapterNo);

            if (reverse) {
                chapterNo--;
                if (chapterNo < to) break;
            } else {
                chapterNo++;
                if (chapterNo > to) break;
            }
        }
    }

    public void onFavChaptersChanged() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mChapterNos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mChapterNos.get(position);
    }

    @NonNull
    @Override
    public VHChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChapterCard chapterCard = new ChapterCard(parent.getContext(), true);

        chapterCard.setBackgroundResource(R.drawable.dr_bg_chapter_card);
        chapterCard.setElevation(Dimen.dp2px(parent.getContext(), 2));

        LayoutParams params = chapterCard.getLayoutParams();
        if (params instanceof MarginLayoutParams) {
            LayoutParamsKt.updateMargins((MarginLayoutParams) params, Dimen.dp2px(parent.getContext(), 5));
        }

        return new VHChapter(chapterCard);
    }

    @Override
    public void onBindViewHolder(@NonNull VHChapter holder, int position) {
        holder.bind(mChapterNos.get(position));
    }

    class VHChapter extends RecyclerView.ViewHolder {
        private final ChapterCard mChapterCard;

        public VHChapter(@NonNull ChapterCard chapterCard) {
            super(chapterCard);
            mChapterCard = chapterCard;
        }

        public void bind(int chapterNo) {
            mChapterCard.setChapterNumber(chapterNo);

            String chapterName = mFragment.getQuranMeta().getChapterName(itemView.getContext(), chapterNo);
            String nameTranslation = mFragment.getQuranMeta().getChapterNameTranslation(chapterNo);
            mChapterCard.setName(chapterName, nameTranslation);

            mChapterCard.setOnClickListener(v -> ReaderFactory.startChapter(v.getContext(), chapterNo));
            mChapterCard.setOnFavoriteButtonClickListener(() -> {
                Context ctx = itemView.getContext();

                FavChaptersViewModel model = mFragment.getFavChaptersModel();
                if (model.isAddedToFavorites(ctx, chapterNo)) model.removeFromFavourites(ctx, chapterNo);
                else model.addToFavourites(ctx, chapterNo);
            });
        }
    }
}
