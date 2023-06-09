package com.goran.quranipiroz.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResult
import com.peacedesign.android.utils.DrawableUtils
import com.peacedesign.android.utils.WindowUtils
import com.goran.quranipiroz.R
import com.goran.quranipiroz.activities.readerSettings.ActivitySettings
import com.goran.quranipiroz.api.JsonHelper
import com.goran.quranipiroz.api.RetrofitInstance
import com.goran.quranipiroz.api.models.tafsir.TafsirModel
import com.goran.quranipiroz.components.quran.subcomponents.Chapter
import com.goran.quranipiroz.databinding.ActivityTafsirBinding
import com.goran.quranipiroz.databinding.LytTafsirHeaderBinding
import com.goran.quranipiroz.utils.Log
import com.goran.quranipiroz.utils.extensions.disableView
import com.goran.quranipiroz.utils.extensions.drawable
import com.goran.quranipiroz.utils.reader.tafsir.TafsirManager
import com.goran.quranipiroz.utils.receivers.NetworkStateReceiver
import com.goran.quranipiroz.utils.sharedPrefs.SPReader
import com.goran.quranipiroz.utils.tafsir.TafsirJsInterface
import com.goran.quranipiroz.utils.tafsir.TafsirUtils
import com.goran.quranipiroz.utils.tafsir.TafsirWebViewClient
import com.goran.quranipiroz.utils.univ.Codes
import com.goran.quranipiroz.utils.univ.FileUtils
import com.goran.quranipiroz.utils.univ.Keys
import com.goran.quranipiroz.utils.univ.ResUtils
import com.goran.quranipiroz.widgets.PageAlert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.util.*

class ActivityTafsir : ReaderPossessingActivity() {
    private lateinit var binding: ActivityTafsirBinding
    private lateinit var fileUtils: FileUtils
    private lateinit var pageAlert: PageAlert
    private lateinit var jsInterface: TafsirJsInterface

    var tafsirKey: String? = null
    var chapterNo = 0
    var verseNo = 0

    override fun getLayoutResource(): Int {
        return R.layout.activity_tafsir
    }

    override fun shouldInflateAsynchronously(): Boolean {
        return false
    }

    override fun preReaderReady(activityView: View, intent: Intent, savedInstanceState: Bundle?) {
        fileUtils = FileUtils.newInstance(this)
        binding = ActivityTafsirBinding.bind(activityView)
        pageAlert = PageAlert(this)
        jsInterface = TafsirJsInterface(this)
        initThis()
    }

    private fun initThis() {
        binding.let {
            it.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            it.settings.setOnClickListener {
                val intent = Intent(this, ActivitySettings::class.java).apply {
                    putExtra(ActivitySettings.KEY_SETTINGS_DESTINATION, ActivitySettings.SETTINGS_TAFSIR)
                }
                startActivity4Result(intent, null)
            }
            it.loader.visibility = View.VISIBLE

        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initContent(intent)
    }

    override fun onReaderReady(intent: Intent, savedInstanceState: Bundle?) {
        initWebView()

        TafsirManager.prepare(this, false) {
            initContent(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.let {
            it.setBackgroundColor(Color.TRANSPARENT)
            it.settings.apply {
                javaScriptEnabled = true
            }
            it.addJavascriptInterface(jsInterface, "TafsirJSInterface")
            it.overScrollMode = View.OVER_SCROLL_NEVER
            it.webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                    Log.d("[" + consoleMessage.lineNumber() + "]" + consoleMessage.message())
                    return true
                }
            }
            it.webViewClient = object : TafsirWebViewClient(this) {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    binding.loader.visibility = View.GONE
                    binding.tafsirHeader.btnPrevVerse.visibility = View.VISIBLE
                    binding.tafsirHeader.btnNextVerse.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getBoilerPlateHTML(): String {
        return ResUtils.readAssetsTextFile(this, "tafsir/tafsir_page.html")
    }

    private fun resolveDarkMode(): String {
        return if (WindowUtils.isNightMode(this)) "dark" else "light"
    }

    private fun resolveTextDirection(): String {
        val model = TafsirManager.getModel(tafsirKey!!)!!
        return if (TextUtils.getLayoutDirectionFromLocale(Locale(model.langCode)) == View.LAYOUT_DIRECTION_RTL) "rtl" else "ltr"
    }

    private fun initContent(intent: Intent) {
        var key = SPReader.getSavedTafsirKey(this)
        val chapterNo = intent.getIntExtra(Keys.READER_KEY_CHAPTER_NO, -1)
        val verseNo = intent.getIntExtra(Keys.READER_KEY_VERSE_NO, -1)

        if (chapterNo < 1 || verseNo < 1) {
            fail("Invalid params", false)
            return
        }

        if (key == null) {
            key = TafsirUtils.getDefaultTafsirKey()
        }

        if (key == null) {
            fail("Failed to load tafsir.", false)
            return
        }

        this.tafsirKey = key
        this.chapterNo = chapterNo
        this.verseNo = verseNo

        initTafsirHeader(binding.tafsirHeader)
        loadContent()
    }

    private fun initTafsirHeader(header: LytTafsirHeaderBinding) {
        val chapter = mQuranRef.get().getChapter(chapterNo)

        setupTafsirTitle(header, chapter)

        val isRTL = bool(R.bool.isRTL)

        header.textPrevTafsir.setDrawables(getStartPointingArrow(this, isRTL), null, null, null)
        header.textNextTafsir.setDrawables(null, null, getEndPointingArrow(this, isRTL), null)

        header.btnPrevVerse.visibility = View.GONE
        header.btnNextVerse.visibility = View.GONE

        val prevVerseName = if (verseNo == 1) "" else getString(R.string.strLabelVerseNo, verseNo - 1)
        val hasPrevVerseName = prevVerseName.isNotEmpty()
        header.btnPrevVerse.disableView(!hasPrevVerseName)
        header.btnPrevVerse.setOnClickListener { jsInterface.previousTafsir() }
        header.prevVerseName.text = if (hasPrevVerseName) prevVerseName else ""
        header.prevVerseName.visibility = if (hasPrevVerseName) View.VISIBLE else View.GONE

        val nextVerseName = if (verseNo == chapter.verseCount) "" else getString(R.string.strLabelVerseNo, verseNo + 1)
        val hasNextVerseName = nextVerseName.isNotEmpty()
        header.btnNextVerse.disableView(!hasNextVerseName)
        header.btnNextVerse.setOnClickListener { jsInterface.nextTafsir() }
        header.nextVerseName.text = if (hasNextVerseName) nextVerseName else ""
        header.nextVerseName.visibility = if (hasNextVerseName) View.VISIBLE else View.GONE
    }

    private fun getStartPointingArrow(context: Context, isRTL: Boolean): Drawable? {
        val arrowLeft = context.drawable(R.drawable.dr_icon_arrow_left)
        return if (!isRTL) arrowLeft else DrawableUtils.rotate(context, arrowLeft, 180f)
    }

    private fun getEndPointingArrow(context: Context, isRTL: Boolean): Drawable? {
        val arrowLeft = context.drawable(R.drawable.dr_icon_arrow_left)
        return if (isRTL) arrowLeft else DrawableUtils.rotate(context, arrowLeft, 180f)
    }

    private fun setupTafsirTitle(header: LytTafsirHeaderBinding, chapter: Chapter) {
        val chapterInfo = SpannableString(
            getString(
                R.string.strLabelVerseWithChapNameWithBar, chapter.name, verseNo
            )
        )

        chapterInfo.setSpan(
            ForegroundColorSpan(color(R.color.colorText2)),
            0,
            chapterInfo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        chapterInfo.setSpan(
            AbsoluteSizeSpan(dimen(R.dimen.dmnCommonSize2)),
            0,
            chapterInfo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        header.tafsirTitle.text = TextUtils.concat(TafsirUtils.getTafsirName(tafsirKey), "\n", chapterInfo)
    }

    private fun loadContent() {
        pageAlert.remove()
        binding.loader.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val tafsirFile = fileUtils.getTafsirFileSingleVerse(tafsirKey, chapterNo, verseNo)
            val slug = TafsirUtils.getTafsirSlugFromKey(tafsirKey)

            if (tafsirFile.length() > 0) {
                val read = tafsirFile.readText()
                val tafsir = JsonHelper.json.decodeFromString<TafsirModel>(read)
                renderData(tafsir)
                return@launch
            }

            if (!NetworkStateReceiver.isNetworkConnected(this@ActivityTafsir)) {
                runOnUiThread { noInternet() }
                return@launch
            }

            try {
                val tafsir = RetrofitInstance.quran.getTafsir(slug, "$chapterNo:$verseNo")["tafsir"]!!

                fileUtils.createFile(tafsirFile)
                tafsirFile.writeText(JsonHelper.json.encodeToString(tafsir))
                renderData(tafsir)
            } catch (e: Exception) {
                e.printStackTrace()
                fail("Failed to load tafsir.", true)
            }
        }
    }

    private fun renderData(tafsir: TafsirModel) {
        val map = mapOf(
            "{{THEME}}" to resolveDarkMode(),
            "{{CONTENT}}" to tafsir.text,
            "{{DIR}}" to resolveTextDirection()
        )

        val pattern = Regex(pattern = map.keys.joinToString("|") { Regex.escape(it) })
        val html = pattern.replace(getBoilerPlateHTML()) { match -> map[match.value].orEmpty() }

        runOnUiThread {
            binding.webView.loadDataWithBaseURL(null, html, "text/html; charset=UTF-8", "utf-8", null)
        }
    }

    private fun fail(msg: String, showRetry: Boolean) {
        binding.loader.visibility = View.GONE

        pageAlert.let {
            it.setMessage(msg, null)
            if (showRetry) {
                it.setActionButton(R.string.strLabelRetry) { loadContent() }
            } else {
                it.setActionButton(null, null)
            }
            it.show(binding.container)
        }
    }

    private fun noInternet() {
        pageAlert.let {
            it.setupForNoInternet { loadContent() }
            it.show(binding.container)
        }
    }

    override fun onActivityResult2(result: ActivityResult?) {
        super.onActivityResult2(result)

        if (result?.resultCode == Codes.SETTINGS_LAUNCHER_RESULT_CODE) {
            tafsirKey = SPReader.getSavedTafsirKey(this)
            loadContent()
        }
    }

    fun scrollToTop() {
        binding.webView.scrollTo(0, 0)
        binding.appBar.setExpanded(true)
    }
}