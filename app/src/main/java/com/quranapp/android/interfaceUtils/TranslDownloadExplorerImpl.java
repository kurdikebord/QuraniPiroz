/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 4/4/2022.
 * All rights reserved.
 */

package com.goran.quranipiroz.interfaceUtils;

import android.view.View;

import com.goran.quranipiroz.adapters.transl.ADPDownloadTransls;
import com.goran.quranipiroz.components.transls.TranslModel;

public interface TranslDownloadExplorerImpl {
    void onDownloadAttempt(ADPDownloadTransls.VHDownloadTransl vhTransl, View referencedView, TranslModel model);
}
