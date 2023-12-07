/*
 * (c) Faisal Khan. Created on 20/11/2021.
 */

package com.goran.quran.karim.dang.interfaceUtils;

import com.goran.quran.karim.dang.components.readHistory.ReadHistoryModel;

public interface ReadHistoryCallbacks {
    void onReadHistoryRemoved(ReadHistoryModel model);

    void onReadHistoryAdded(ReadHistoryModel model);
}
