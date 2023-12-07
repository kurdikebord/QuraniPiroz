/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 4/4/2022.
 * All rights reserved.
 */

package com.goran.quran.karim.dang.interfaceUtils;

import android.view.View;

import com.goran.quran.karim.dang.adapters.transl.ADPDownloadTranslations;
import com.goran.quran.karim.dang.components.transls.TranslModel;

public interface TranslDownloadExplorerImpl {
    void onDownloadAttempt(ADPDownloadTranslations adapter, ADPDownloadTranslations.VHDownloadTransl vhTransl, View referencedView, TranslModel model);
}
