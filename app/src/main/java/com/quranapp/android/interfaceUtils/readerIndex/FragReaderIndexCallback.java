/*
 * (c) Faisal Khan. Created on 19/2/2022.
 */

package com.goran.quranipiroz.interfaceUtils.readerIndex;

import android.content.Context;

public interface FragReaderIndexCallback {
    void scrollToTop(boolean smooth);

    void sort(Context ctx);
}
