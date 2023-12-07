package com.goran.quran.karim.dang.utils.quranScience

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.webkit.WebViewClientCompat
import com.goran.quran.karim.dang.activities.ActivityReader
import com.goran.quran.karim.dang.activities.reference.ActivityQuranScienceContent
import com.goran.quran.karim.dang.utils.reader.factory.ReaderFactory
import com.goran.quran.karim.dang.utils.reader.getQuranScriptFontRes
import com.goran.quran.karim.dang.utils.reader.toKFQPCFontFilename
import com.goran.quran.karim.dang.utils.sharedPrefs.SPReader
import com.goran.quran.karim.dang.utils.univ.Keys.READER_KEY_SAVE_TRANSL_CHANGES
import com.goran.quran.karim.dang.utils.univ.Keys.READER_KEY_TRANSL_SLUGS
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
import java.util.*


open class QuranScienceWebViewClient(private val activity: ActivityQuranScienceContent) : WebViewClientCompat() {
    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        try {
            val webResourceResponse = handleRequest(view, request)
            if (webResourceResponse != null) {
                return webResourceResponse
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return super.shouldInterceptRequest(view, request)
    }


    @Throws(IOException::class)
    private fun handleRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        val uri = request.url
        val host = uri.host ?: return null
        val ctx = view.context
        var data: InputStream? = null
        val uriStr = uri.toString().lowercase(Locale.getDefault())
        when (host) {
            "assets-file" -> data = ctx.assets.open(uri.toString().substring("https://assets-file/".length))
            "assets-font" -> {
                if (uriStr.contains("quran-arabic")) {
                    val savedScript = SPReader.getSavedScript(view.context)
                    val decorator = activity.mVerseDecorator
                    if (decorator.isKFQPCScript()) {
                        val pageNo = uri.lastPathSegment!!.split("_").last().toInt()
                        val file = File(decorator.fileUtils.getKFQPCScriptFontDir(savedScript), pageNo.toKFQPCFontFilename())
                        data = FileInputStream(file)
                    } else {
                        data = ctx.resources.openRawResource(+savedScript.getQuranScriptFontRes())
                    }
                }
            }
        }

        if (data == null) {
            return null
        }

        val headers = request.requestHeaders

        headers["Access-Control-Allow-Origin"] = "*"

        return WebResourceResponse(
                URLConnection.guessContentTypeFromName(uri.path),
                "utf-8",
                200,
                "OK",
                headers,
                data
        )
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val uri = request.url
        val host = uri.host ?: return true

        val pathSegments = uri.pathSegments
        if (pathSegments.size == 2 && host.contains("quranapp.verse")) {
            val chapterNo = pathSegments[0].toInt()
            val verses = pathSegments[1].split("-")
            val fromVerse = verses[0].toInt()
            var toVerse = fromVerse
            if (verses.size > 1) {
                toVerse = verses[1].toInt()
            }

            if (host == "quranapp.verse.ref") {
                activity.mActionController.showReferenceSingleVerseOrRange(activity.slugs, chapterNo, Pair(fromVerse, toVerse))
            } else if (host == "quranapp.verse.reader") {
                activity.startActivity(ReaderFactory.prepareVerseRangeIntent(chapterNo, fromVerse, toVerse).apply {
                    setClass(activity, ActivityReader::class.java)
                    putExtra(READER_KEY_TRANSL_SLUGS, activity.slugs.toTypedArray<String>())
                    putExtra(READER_KEY_SAVE_TRANSL_CHANGES, false)
                })
            }
        }

        return true
    }
}