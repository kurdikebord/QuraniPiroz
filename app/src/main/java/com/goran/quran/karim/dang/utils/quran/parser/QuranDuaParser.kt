package com.goran.quran.karim.dang.utils.quran.parser

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.goran.quran.karim.dang.components.quran.QuranMeta
import com.goran.quran.karim.dang.components.quran.ExclusiveVerse
import com.goran.quran.karim.dang.utils.Log
import java.util.*
import java.util.concurrent.atomic.AtomicReference

object QuranDuaParser : ExclusiveVersesParser() {
    fun parseDua(
        context: Context,
        quranMeta: QuranMeta,
        quranDuaRef: AtomicReference<List<ExclusiveVerse>>,
        postRunnable: Runnable
    ) {
        Thread {
            try {
                quranDuaRef.set(
                    parseFromAssets(
                        context,
                        quranMeta,
                        "type1"
                    )
                )
            } catch (e: Exception) {
                Log.saveError(e, "QuranDuaParser.parseDua")
            }

            Handler(Looper.getMainLooper()).post(postRunnable)
        }.start()
    }
}
