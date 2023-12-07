package com.goran.quran.karim.dang.utils.reader

import android.content.Context
import androidx.annotation.DimenRes
import com.goran.quran.karim.dang.R
import com.goran.quran.karim.dang.components.quran.QuranMeta
import com.goran.quran.karim.dang.utils.app.AppUtils
import com.goran.quran.karim.dang.utils.univ.FileUtils
import java.io.File
import java.util.*

object QuranScriptUtils {
    val FONTS_DIR_NAME: String = FileUtils.createPath(
        AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR,
        "script_fonts"
    )
    val SCRIPT_DIR_NAME: String = FileUtils.createPath(
        AppUtils.BASE_APP_DOWNLOADED_SAVED_DATA_DIR,
        "scripts"
    )

    const val KEY_SCRIPT = "key.script"

    const val SCRIPT_UTHMANI = "uthmani"
    const val SCRIPT_UTHMANI_TAJWEED = "uthmani_tajweed"
    const val SCRIPT_INDO_PAK = "indopak"
    const val SCRIPT_NOOREHUDA = "noorehuda"
    const val SCRIPT_KFQPC_V1 = "kfqpc_v1"


    const val PREVIEW_TEXT_UTHMANI = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ ١"
    const val PREVIEW_TEXT_UTHMANI_TAJWEED = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ ١"
    const val PREVIEW_TEXT_INDOPAK = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِیْمِ "
    const val PREVIEW_TEXT_NOOREHUDA = "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِیْمِ ﴿﴾"
    const val PREVIEW_TEXT_KFQPC_V1 = "ﭑ ﭒ ﭓ ﭔ ﭕ"

    const val SCRIPT_DEFAULT = SCRIPT_UTHMANI
    const val TOTAL_DOWNLOAD_PARTS = 4

    val INDO_PAK_SCRIPT_NAMES = mapOf(
        "en"  to "IndoPak",
        "ar"  to "نستعليق",
        "ckb" to "هیندوپاک",
        "fa"  to "هند پاک",
        "ur"  to "انڈو پاک",
    )

    val UTHMANI_SCRIPT_NAMES = mapOf(
        "en"  to "Uthmani Hafs",
        "ar"  to "العثماني حفص",
        "ckb" to "حەفسی عوسمانی",
        "fa"  to "عثمانی حفص",
        "ur"  to "عثمانی حفص",
    )

    val UTHMANI_TAJWEED_SCRIPT_NAMES = mapOf(
        "en"  to "Uthmani Tajweed",
        "ar"  to "العثماني تجوید",
        "ckb" to "عوسمانی تەجووید",
        "fa"  to "عثمانی تجوید",
        "ur"  to "عثمانی تجوید",
    )

    val KFQPC_SCRIPT_NAMES = mapOf(
        "en"  to "King Fahd Complex V1",
        "ar"  to "مجمع الملك فهد الإصدار 1",
        "ckb" to "لێکدراوی پاشا فەهد v1",
        "fa"  to "مجتمع شاه فهد V1",
        "ur"  to "کنگ فہد کمپلیکس V1",
    )

    val NOOREHUDA_SCRIPT_NAMES = mapOf(
        "en"  to "Noorehuda",
        "ar"  to "نورلهدا",
        "ckb" to "نورهودا",
        "fa"  to "نورلهدا",
        "ur"  to "نورلهدا",
    )

    fun availableScriptSlugs(): Array<String> = arrayOf(
        SCRIPT_UTHMANI,
        SCRIPT_UTHMANI_TAJWEED,
        SCRIPT_INDO_PAK,
        SCRIPT_NOOREHUDA,
        SCRIPT_KFQPC_V1
    )

    fun verifyKFQPCScriptDownloaded(ctx: Context, kfqpcScriptSlug: String): Boolean {
        return FileUtils.newInstance(ctx).getScriptFile(kfqpcScriptSlug).length() > 0
    }

    /**
     * Get the summary of the KFQPC font download.
     * @return A triple containing counts of the total pages, total downloaded, and the remaining.
     */
    fun getKFQPCFontDownloadedCount(ctx: Context, kfqpcScriptSlug: String): Triple<Int, Int, Int> {
        val fileUtils = FileUtils.newInstance(ctx)
        val kfqpcScriptFontDir = fileUtils.getKFQPCScriptFontDir(kfqpcScriptSlug)
        val totalPages = QuranMeta.totalPages()

        var downloaded = 0

        for (pageNo in 1..totalPages) {
            if (File(kfqpcScriptFontDir, pageNo.toKFQPCFontFilename()).length() > 0L) {
                downloaded++
            }
        }

        return Triple(totalPages, downloaded, totalPages - downloaded)
    }
}

fun String.isKFQPCScript(): Boolean = when (this) {
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> true
    else -> false
}

fun String.getQuranScriptName(): String {
    val mapToQuery: Map<String, String> = when (this) {
        QuranScriptUtils.SCRIPT_UTHMANI -> QuranScriptUtils.UTHMANI_SCRIPT_NAMES
        QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> QuranScriptUtils.UTHMANI_TAJWEED_SCRIPT_NAMES
        QuranScriptUtils.SCRIPT_KFQPC_V1 -> QuranScriptUtils.KFQPC_SCRIPT_NAMES
        QuranScriptUtils.SCRIPT_NOOREHUDA -> QuranScriptUtils.NOOREHUDA_SCRIPT_NAMES
        else -> QuranScriptUtils.INDO_PAK_SCRIPT_NAMES
    }

    return mapToQuery[Locale.getDefault().toLanguageTag()] ?: mapToQuery["en"]!!
}

fun String.getScriptPreviewText(): String = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> QuranScriptUtils.PREVIEW_TEXT_UTHMANI
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> QuranScriptUtils.PREVIEW_TEXT_UTHMANI_TAJWEED
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> QuranScriptUtils.PREVIEW_TEXT_KFQPC_V1
    QuranScriptUtils.SCRIPT_NOOREHUDA -> QuranScriptUtils.PREVIEW_TEXT_NOOREHUDA
    else -> QuranScriptUtils.PREVIEW_TEXT_INDOPAK
}

@DimenRes
fun String.getQuranScriptVerseTextSizeSmallRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.dimen.dmnReaderTextSizeArUthmaniSmall
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.dimen.dmnReaderTextSizeArUthmaniSmall
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.dimen.dmnReaderTextSizeArKFQPCSmall
    QuranScriptUtils.SCRIPT_NOOREHUDA -> R.dimen.dmnReaderTextSizeArNoorehudaSmall
    else -> R.dimen.dmnReaderTextSizeArIndoPakSmall
}

@DimenRes
fun String.getQuranScriptVerseTextSizeMediumRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.dimen.dmnReaderTextSizeArUthmaniMedium
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.dimen.dmnReaderTextSizeArUthmaniMedium
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.dimen.dmnReaderTextSizeArKFQPCMedium
    QuranScriptUtils.SCRIPT_NOOREHUDA -> R.dimen.dmnReaderTextSizeArNoorehudaMedium
    else -> R.dimen.dmnReaderTextSizeArIndoPakMedium
}

fun String.getQuranScriptFontRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.font.uthmanic_hafs
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.font.uthmanic_tajweed
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.font.qpc_page_1
    QuranScriptUtils.SCRIPT_NOOREHUDA -> R.font.noorehuda
    else -> R.font.indopak
}

fun String.getQuranScriptResPath(): String = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> "scripts/script_uthmani_hafs.json"
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> "scripts/script_uthmani_hafs.json"
    QuranScriptUtils.SCRIPT_NOOREHUDA -> "scripts/script_noorehuda.json"
    else -> "scripts/script_indopak.json"
}

fun Int.toKFQPCFontFilename(): String {
    return "qpc_page_%03d.TTF".format(Locale.ENGLISH, this)
}
