/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 19/3/2022.
 * All rights reserved.
 */

package com.goran.quranipiroz.activities.test;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.goran.quranipiroz.R;
import com.goran.quranipiroz.activities.base.BaseActivity;
import com.goran.quranipiroz.components.quran.subcomponents.Translation;
import com.goran.quranipiroz.databinding.ActivityExperimentBinding;
import com.goran.quranipiroz.utils.Log;
import com.goran.quranipiroz.utils.reader.TranslUtils;
import com.goran.quranipiroz.utils.reader.factory.QuranTranslationFactory;
import com.goran.quranipiroz.utils.reader.factory.ReaderFactory;
import com.goran.quranipiroz.views.BoldHeader;

import java.util.Collections;
import java.util.List;

public class ActivityExperiment extends BaseActivity {
    private ActivityExperimentBinding mBinding;

    @Override
    protected boolean shouldInflateAsynchronously() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_experiment;
    }

    @Override
    protected void onActivityInflated(@NonNull View activityView, @Nullable Bundle savedInstanceState) {
        mBinding = ActivityExperimentBinding.bind(activityView);
        initHeader(mBinding.header);
        mBinding.reader.setOnClickListener(v -> ReaderFactory.startChapter(this, 1));
        mBinding.perform.setOnClickListener(v -> perform());
    }

    private void initHeader(BoldHeader header) {
        header.setCallback(this::onBackPressed);
        header.setTitleText("Experiment");

        header.setShowRightIcon(false);

        header.setBGColor(R.color.colorBGPage);
    }

    private void perform() {
        QuranTranslationFactory translFactory = new QuranTranslationFactory(this);

        List<Translation> transls = translFactory.getTranslationsSingleVerse(1, 1);
        Log.d(transls);

        int[] ints = {1, 7, 2, 3};
        Log.d(translFactory.getTranslationsDistinctVerses(
            Collections.singleton(TranslUtils.TRANSL_SLUG_EN_THE_CLEAR_QURAN), 88,
            ints));

        Log.d(translFactory.getTranslationsVerseRange(2, 10, 12));
        Log.d(translFactory.getTranslationBookInfo(TranslUtils.TRANSL_SLUG_EN_THE_CLEAR_QURAN));
    }
}