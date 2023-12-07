/*
 * (c) Faisal Khan. Created on 31/10/2021.
 */

package com.goran.quran.karim.dang.interfaceUtils;

import android.content.Context;

public interface OnTranslSelectionChangeListener<R> {
    boolean onSelectionChanged(Context ctx, R r, boolean isSelected);
}