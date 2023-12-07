package com.goran.quran.karim.dang.db.transl;

import android.provider.BaseColumns;

public final class QuranTranslContract {
    private QuranTranslContract() {}

    public static class QuranTranslEntry implements BaseColumns {
        public static final String TABLE_NAME = "Translation";
        public static final String COL_CHAPTER_NO = "chapterNo";
        public static final String COL_VERSE_NO = "verseNo";
        public static final String COL_TEXT = "text";
        public static final String COL_FOOTNOTES = "footnotes";
    }
}