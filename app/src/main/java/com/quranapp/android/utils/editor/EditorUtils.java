/*
 * (c) Faisal Khan. Created on 9/2/2022.
 */

package com.goran.quranipiroz.utils.editor;

import com.goran.quranipiroz.frags.editshare.FragEditorBG;
import com.goran.quranipiroz.frags.editshare.FragEditorBase;
import com.goran.quranipiroz.frags.editshare.FragEditorFG;
import com.goran.quranipiroz.frags.editshare.FragEditorOptions;
import com.goran.quranipiroz.frags.editshare.FragEditorSize;
import com.goran.quranipiroz.frags.editshare.FragEditorTransls;

import java.util.HashMap;
import java.util.Map;

public class EditorUtils {
    private final Map<String, Integer> fragsIndices = new HashMap<>();

    public void setFragIndex(Class<? extends FragEditorBase> fragClass, int index) {
        fragsIndices.put(fragClass.getSimpleName(), index);
    }

    public Integer getBackgroundsFragIndex() {
        return fragsIndices.get(FragEditorBG.class.getSimpleName());
    }

    public Integer getColorsFragIndex() {
        return fragsIndices.get(FragEditorFG.class.getSimpleName());
    }

    public Integer getOptionsFragIndex() {
        return fragsIndices.get(FragEditorOptions.class.getSimpleName());
    }

    public Integer getTranslationsFragIndex() {
        return fragsIndices.get(FragEditorTransls.class.getSimpleName());
    }

    public Integer getSizeFragIndex() {
        return fragsIndices.get(FragEditorSize.class.getSimpleName());
    }
}
