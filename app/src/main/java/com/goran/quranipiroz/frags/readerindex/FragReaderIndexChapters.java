package com.goran.quranipiroz.frags.readerindex;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

import com.peacedesign.android.utils.WindowUtils;
import com.goran.quranipiroz.adapters.quranIndex.ADPChaptersList;
import com.goran.quranipiroz.views.helper.RecyclerView2;

public class FragReaderIndexChapters extends BaseFragReaderIndex {
    public FragReaderIndexChapters() {
    }

    public static FragReaderIndexChapters newInstance() {
        return new FragReaderIndexChapters();
    }

    @Override
    protected void initList(RecyclerView2 list, Context ctx) {
        super.initList(list, ctx);

        mHandler.post(() -> {
            int spanCount = WindowUtils.isLandscapeMode(ctx) ? 2 : 1;
            GridLayoutManager layoutManager = new GridLayoutManager(ctx, spanCount, VERTICAL, false);
            list.setLayoutManager(layoutManager);
        });

        resetAdapter(list, ctx, false);
    }

    @Override
    protected void resetAdapter(RecyclerView2 list, Context ctx, boolean reverse) {
        super.resetAdapter(list, ctx, reverse);
        ADPChaptersList adapter = new ADPChaptersList(this, ctx, reverse);
        mHandler.post(() -> list.setAdapter(adapter));
    }
}