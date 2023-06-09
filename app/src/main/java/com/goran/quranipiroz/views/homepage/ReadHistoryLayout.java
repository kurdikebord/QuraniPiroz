/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 28/2/2022.
 * All rights reserved.
 */

package com.goran.quranipiroz.views.homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import static com.peacedesign.android.utils.Dimen.dp2px;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import com.goran.quranipiroz.R;
import com.goran.quranipiroz.activities.ActivityReadHistory;
import com.goran.quranipiroz.adapters.ADPReadHistory;
import com.goran.quranipiroz.components.quran.QuranMeta;
import com.goran.quranipiroz.components.readHistory.ReadHistoryModel;
import com.goran.quranipiroz.databinding.LytHomepageTitledItemTitleBinding;
import com.goran.quranipiroz.db.readHistory.ReadHistoryDBHelper;
import com.goran.quranipiroz.interfaceUtils.Destroyable;
import com.goran.quranipiroz.utils.extensions.TextViewKt;
import com.goran.quranipiroz.utils.extensions.ViewKt;
import com.goran.quranipiroz.utils.extensions.ViewPaddingKt;
import com.goran.quranipiroz.utils.thread.runner.CallableTaskRunner;
import com.goran.quranipiroz.utils.thread.tasks.BaseCallableTask;

import java.util.List;

public class ReadHistoryLayout extends HomepageCollectionLayoutBase implements Destroyable {
    private final CallableTaskRunner<List<ReadHistoryModel>> taskRunner = new CallableTaskRunner<>();
    private final ReadHistoryDBHelper mReadHistoryDBHelper;
    private ADPReadHistory mAdapter;
    private boolean mFirstTime = true;

    public ReadHistoryLayout(@NonNull Context context) {
        this(context, null);
    }

    public ReadHistoryLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadHistoryLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mReadHistoryDBHelper = new ReadHistoryDBHelper(context);
    }

    @Override
    public void destroy() {
        if (mReadHistoryDBHelper != null) {
            mReadHistoryDBHelper.close();
        }
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.strTitleReadHistory;
    }

    @Override
    protected int getHeaderIcon() {
        return R.drawable.dr_icon_history;
    }

    @Override
    protected boolean showViewAllBtn() {
        return true;
    }

    @Override
    protected void setupHeader(Context context, LytHomepageTitledItemTitleBinding header) {
        header.titleIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    @Override
    protected void onViewAllClick(Context context) {
        context.startActivity(new Intent(context, ActivityReadHistory.class));
    }

    private void refreshHistory(QuranMeta quranMeta) {
        if (!taskRunner.isDone()) {
            taskRunner.cancel();
        }

        taskRunner.callAsync(new BaseCallableTask<List<ReadHistoryModel>>() {
            @Override
            public void preExecute() {
                if (mFirstTime) {
                    showLoader();
                    mFirstTime = false;
                }
            }

            @Override
            public List<ReadHistoryModel> call() {
                return mReadHistoryDBHelper.getAllHistories(10);
            }

            @Override
            public void onComplete(List<ReadHistoryModel> models) {
                hideLoader();

                if (models.size() == 0) {
                    makeNoHistoryAlert();
                } else {
                    if (mAdapter == null) {
                        mAdapter = new ADPReadHistory(getContext(), quranMeta, models, dp2px(getContext(), 280));
                        resolveListView().setAdapter(mAdapter);
                    } else {
                        mAdapter.updateModels(models);
                    }
                }
            }
        });
    }

    private void makeNoHistoryAlert() {
        View list = findViewById(R.id.list);
        if (list != null) {
            ViewKt.removeView(list);
        }

        if (findViewById(R.id.text) != null) {
            return;
        }

        Context ctx = getContext();
        AppCompatTextView tv = new AppCompatTextView(ctx);
        tv.setId(R.id.text);

        ViewPaddingKt.updatePaddings(tv, dp2px(ctx, 20), dp2px(ctx, 15));
        TextViewKt.setTextSizePx(tv, R.dimen.dmnCommonSize1_5);
        TextViewKt.setTextColorResource(tv, R.color.colorText2);

        tv.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
        tv.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        tv.setText(R.string.strMsgReadShowupHere);
        addView(tv);
    }

    @Override
    protected RecyclerView resolveListView() {
        ViewKt.removeView(findViewById(R.id.text));
        return super.resolveListView();
    }

    public void refresh(QuranMeta quranMeta) {
        refreshHistory(quranMeta);
    }
}
