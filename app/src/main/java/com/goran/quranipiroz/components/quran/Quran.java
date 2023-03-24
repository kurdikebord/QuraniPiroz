package com.goran.quranipiroz.components.quran;

import android.content.Context;
import androidx.annotation.Nullable;

import com.goran.quranipiroz.components.quran.subcomponents.Chapter;
import com.goran.quranipiroz.components.quran.subcomponents.Verse;
import com.goran.quranipiroz.interfaceUtils.OnResultReadyCallback;
import com.goran.quranipiroz.utils.quran.parser.QuranParser;
import com.goran.quranipiroz.utils.sharedPrefs.SPReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import kotlin.Unit;

public class Quran {
    private static final AtomicReference<Quran> sQuranRef = new AtomicReference<>();
    private final String mScript;
    private final Map<Integer, Chapter> chapters;

    public Quran(String script, Map<Integer, Chapter> chapters) {
        mScript = script;
        this.chapters = chapters;
    }

    private Quran(Quran quran) {
        mScript = quran.mScript;
        chapters = new HashMap<>();
        quran.chapters.forEach((chapterNo, chapter) -> chapters.put(chapterNo, chapter.copy()));
    }


    public static void prepareInstance(Context context, @Nullable QuranMeta quranMeta, OnResultReadyCallback<Quran> resultReadyCallback) {
        String savedScript = SPReader.getSavedScript(context);

        if (sQuranRef.get() == null || !Objects.equals(sQuranRef.get().mScript, savedScript)) {
            synchronized (Quran.class) {
                if (sQuranRef.get() == null || !Objects.equals(sQuranRef.get().mScript, savedScript)) {
                    prepare(context, savedScript, quranMeta, resultReadyCallback);
                } else {
                    resultReadyCallback.onReady(sQuranRef.get());
                }
            }
        } else {
            resultReadyCallback.onReady(sQuranRef.get());
        }
    }

    private static void prepare(Context context, String script, @Nullable QuranMeta quranMeta, OnResultReadyCallback<Quran> resultReadyCallback) {
        QuranParser parser = new QuranParser(context);
        parser.parse(script, quranMeta, sQuranRef, () -> {
            resultReadyCallback.onReady(sQuranRef.get());
            return Unit.INSTANCE;
        });
    }

    public String getScript() {
        return mScript;
    }

    public Map<Integer, Chapter> getChapters() {
        return chapters;
    }

    public Chapter getChapter(int chapterNo) {
        return chapters.get(chapterNo);
    }

    public Verse getVerse(int chapterNo, int verseNo) {
        Chapter chapter = getChapter(chapterNo);
        if (chapter == null) {
            return null;
        }
        return chapter.getVerse(verseNo);
    }

    public Quran copy() {
        return new Quran(this);
    }
}
