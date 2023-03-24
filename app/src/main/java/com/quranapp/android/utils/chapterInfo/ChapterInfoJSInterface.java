package com.goran.quranipiroz.utils.chapterInfo;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.goran.quranipiroz.activities.ActivityChapInfo;
import com.goran.quranipiroz.components.quran.QuranMeta;
import com.goran.quranipiroz.utils.Log;
import com.goran.quranipiroz.utils.reader.TranslUtils;
import com.goran.quranipiroz.utils.univ.MessageUtils;

import kotlin.Pair;

public class ChapterInfoJSInterface {
    private final ActivityChapInfo mActivity;

    public ChapterInfoJSInterface(ActivityChapInfo activityChapInfo) {
        mActivity = activityChapInfo;
    }

    @JavascriptInterface
    public void openReference(int chapterNo, int fromVerse, int toVerse) {
        QuranMeta quranMeta = mActivity.mQuranMeta;

        if (!QuranMeta.isChapterValid(chapterNo) || !quranMeta.isVerseRangeValid4Chapter(chapterNo, fromVerse,
            toVerse)) {
            Log.d(chapterNo, fromVerse, toVerse);
            MessageUtils.showRemovableToast(mActivity, "Could not open references", Toast.LENGTH_LONG);
            return;
        }

        mActivity.showReferenceSingleVerseOrRange(
            TranslUtils.defaultTranslationSlugs(),
            chapterNo, new Pair<>(fromVerse, toVerse)
        );
    }
}
