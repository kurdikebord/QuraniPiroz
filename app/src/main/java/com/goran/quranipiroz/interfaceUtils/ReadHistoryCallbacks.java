/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.goran.quranipiroz.interfaceUtils;

import com.goran.quranipiroz.components.readHistory.ReadHistoryModel;

public interface ReadHistoryCallbacks {
    void onReadHistoryRemoved(ReadHistoryModel model);

    void onReadHistoryAdded(ReadHistoryModel model);
}
