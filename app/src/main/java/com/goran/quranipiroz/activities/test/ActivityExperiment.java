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
    }
}