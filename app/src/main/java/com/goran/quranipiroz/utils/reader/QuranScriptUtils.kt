package com.goran.quranipiroz.utils.reader

import android.content.Context
import androidx.annotation.DimenRes
import com.goran.quranipiroz.R
import com.goran.quranipiroz.components.quran.QuranMeta
import com.goran.quranipiroz.utils.app.AppUtils
import com.goran.quranipiroz.utils.univ.FileUtils
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
    const val SCRIPT_KFQPC_V1 = "kfqpc_v1"

    const val SCRIPT_DEFAULT = SCRIPT_UTHMANI

    val UTHMANI_SCRIPT_NAMES = mapOf(
        "en" to "Uthmani Hafs",
        "ar" to "العثماني حفص",
        "bn" to "উসমানী হাফস",
        "ckb" to "حەفسی عوسمانی",
        "de" to "Uthmani Hafs",
        "es" to "Uthmani Hafs",
        "fa" to "عثمانی حفص",
        "fr" to "Uthmani Hafs",
        "hi" to "उशमनी हफ्स",
        "in" to "Utsmani Hafs",
        "it" to "Uthmani Hafs",
        "ml" to "ഓട്ടോമൻ ഹാഫുകൾ",
        "pt" to "Uthmani Hafs",
        "tr" to "Osmanca Hafs",
        "ur" to "عثمانی حفص",
    )
    val UTHMANI_TAJWEED_SCRIPT_NAMES = mapOf(
        "en" to "Uthmani Tajweed",
        "ar" to "العثماني تجويد",
        "bn" to "অটোমান স্বরধ্বনি",
        "ckb" to "تەجویدی عوسمانی",
        "de" to "Uthmani Tajweed",
        "es" to "Uthmani Tajweed",
        "fa" to "عثمانی تجوید",
        "fr" to "Uthmani Tajweed",
        "hi" to "ओटोमन इंटोनेशन",
        "in" to "Utsmani Tajweed",
        "it" to "Uthmani Tajweed",
        "ml" to "ഒട്ടോമൻ സ്വരം",
        "pt" to "Uthmani Tajweed",
        "tr" to "Osmanca Tonlaması",
        "ur" to "عثمانی تجوید",
    )
    val INDO_PAK_SCRIPT_NAMES = mapOf(
        "en" to "IndoPak",
        "ar" to "نستعليق",
        "bn" to "ইন্দোপাক",
        "ckb" to "هیندوپاک",
        "de" to "IndoPak",
        "es" to "IndoPak",
        "fa" to "هند پاک",
        "fr" to "IndoPak",
        "hi" to "इंडो पाक",
        "in" to "IndoPak",
        "it" to "IndoPak",
        "ml" to "ഇൻഡോപാക്",
        "pt" to "IndoPak",
        "tr" to "Hint Paketi",
        "ur" to "انڈو پاک",
    )

    val KFQPC_SCRIPT_NAMES = mapOf(
        "en" to "King Fahd Complex V1",
        "ar" to "مجمع الملك فهد الإصدار 1",
        "bn" to "কিং ফাহাদ কমপ্লেক্স V1",
        "ckb" to "لێکدراوی پاشا فەهد v1",
        "de" to "König Fahd Komplex V1",
        "es" to "Rey Fahd Complex V1",
        "fa" to "مجتمع شاه فهد V1",
        "fr" to "Complexe Roi Fahad V1",
        "hi" to "राजा फहद कॉम्प्लेक्स v1",
        "in" to "Kompleks Raja Fahad V1",
        "it" to "Complesso di Re Fahad V1",
        "ml" to "കിംഗ് ഫഹദ് സമുച്ചയം v1",
        "pt" to "Complexo King Fahad V1",
        "tr" to "Kral Fehd Kompleksi V1",
        "ur" to "کنگ فہد کمپلیکس V1",
    )

    fun availableScriptSlugs(): Array<String> = arrayOf(
        SCRIPT_UTHMANI,
        SCRIPT_UTHMANI_TAJWEED,
        SCRIPT_INDO_PAK,
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
        else -> QuranScriptUtils.INDO_PAK_SCRIPT_NAMES
    }

    return mapToQuery[Locale.getDefault().toLanguageTag()] ?: mapToQuery["en"]!!
}

fun String.getScriptPreviewRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.string.strScriptPreviewUthmani
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.string.strScriptPreviewUthmani
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.string.strScriptPreviewKFQPC_V1
    else -> R.string.strScriptPreviewIndopak}

@DimenRes
fun String.getQuranScriptVerseTextSizeSmallRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.dimen.dmnReaderTextSizeArUthmaniSmall
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.dimen.dmnReaderTextSizeArUthmaniSmall
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.dimen.dmnReaderTextSizeArKFQPCSmall
    else -> R.dimen.dmnReaderTextSizeArIndoPakSmall
}

@DimenRes
fun String.getQuranScriptVerseTextSizeMediumRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.dimen.dmnReaderTextSizeArUthmaniMedium
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.dimen.dmnReaderTextSizeArUthmaniMedium
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.dimen.dmnReaderTextSizeArKFQPCMedium
    else -> R.dimen.dmnReaderTextSizeArIndoPakMedium
}

fun String.getQuranScriptFontRes(): Int = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> R.font.uthmanic_hafs
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> R.font.uthmanic_tajweed
    QuranScriptUtils.SCRIPT_KFQPC_V1 -> R.font.qpc_page_1
    else -> R.font.indopak
}

fun String.getQuranScriptResPath(): String = when (this) {
    QuranScriptUtils.SCRIPT_UTHMANI -> "scripts/script_uthmani_hafs.json"
    QuranScriptUtils.SCRIPT_UTHMANI_TAJWEED -> "scripts/script_uthmani_hafs.json"
    else -> "scripts/script_indopak.json"
}

fun Int.toKFQPCFontFilename(): String {
    return "qpc_page_$this.TTF"
}