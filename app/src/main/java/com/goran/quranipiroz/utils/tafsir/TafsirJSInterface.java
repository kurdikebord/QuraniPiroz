package com.goran.quranipiroz.utils.tafsir;

import android.content.ComponentName;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.goran.quranipiroz.activities.ActivityReader;
import com.goran.quranipiroz.activities.ActivityTafsir2;
import com.goran.quranipiroz.utils.reader.factory.ReaderFactory;
import com.goran.quranipiroz.utils.univ.Codes;
import com.goran.quranipiroz.utils.univ.Keys;

public class TafsirJSInterface {
    private final ActivityTafsir2 mActivityTafsir;

    public TafsirJSInterface(ActivityTafsir2 activityTafsir) {
        mActivityTafsir = activityTafsir;
    }

    @JavascriptInterface
    public void goToVerse() {
        Intent intent = ReaderFactory.prepareSingleVerseIntent(mActivityTafsir.mChapterNo, mActivityTafsir.mVerseNo);

        ComponentName callingActivity = mActivityTafsir.getCallingActivity();
        if (callingActivity != null && ActivityReader.class.getName().equals(callingActivity.getClassName())) {
            mActivityTafsir.setResult(Codes.OPEN_REFERENCE_RESULT_CODE, intent);
            mActivityTafsir.finish();
        } else {
            intent = intent.setClass(mActivityTafsir, ActivityReader.class);
            mActivityTafsir.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void previousTafsir() {
        if (mActivityTafsir.mVerseNo == 1) {
            return;
        }
        Intent intent = mActivityTafsir.getIntent();
        intent.putExtra(Keys.READER_KEY_VERSE_NO, mActivityTafsir.mVerseNo - 1);
        intent.setAction(null);
        mActivityTafsir.startActivity(intent);
    }

    @JavascriptInterface
    public void nextTafsir() {
        if (mActivityTafsir.mVerseNo == mActivityTafsir.mQuranMeta.getChapterVerseCount(mActivityTafsir.mChapterNo)) {
            return;
        }

        Intent intent = mActivityTafsir.getIntent();
        intent.putExtra(Keys.READER_KEY_VERSE_NO, mActivityTafsir.mVerseNo + 1);
        intent.setAction(null);
        mActivityTafsir.startActivity(intent);
    }
}
