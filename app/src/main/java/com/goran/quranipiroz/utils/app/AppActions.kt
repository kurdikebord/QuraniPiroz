package com.goran.quranipiroz.utils.app

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import com.peacedesign.android.utils.AppBridge
import com.peacedesign.android.widget.dialog.base.PeaceDialog
import com.goran.quranipiroz.R
import com.goran.quranipiroz.api.ApiConfig
import com.goran.quranipiroz.api.RetrofitInstance
import com.goran.quranipiroz.utils.extensions.copyToClipboard
import com.goran.quranipiroz.utils.reader.factory.QuranTranslationFactory
import com.goran.quranipiroz.utils.services.TranslationDownloadService
import com.goran.quranipiroz.utils.sharedPrefs.SPAppActions
import com.goran.quranipiroz.utils.sharedPrefs.SPAppConfigs
import com.goran.quranipiroz.utils.sharedPrefs.SPLog
import com.goran.quranipiroz.utils.votd.VOTDUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object AppActions {
    const val APP_ACTION_KEY = "app.action.key"
    const val APP_ACTION_VICTIM_KEY = "app.action.victim_key"
    const val APP_ACTION_TRANSL_UPDATE = "app.action.translation.update"
    const val APP_ACTION_URLS_UPDATE = "app.action.urls_update"

    private fun updateTransl(ctx: ContextWrapper, slug: String) {
        val factory = QuranTranslationFactory(ctx)
        val translationExists = factory.isTranslationDownloaded(slug)
        if (translationExists) {
            val bookInfo = factory.getTranslationBookInfo(slug)

            // The slug could be empty. Check factory.getTranslationBookInfo(slug) for more info.
            if (bookInfo.slug.isNotEmpty()) {
                TranslationDownloadService.startDownloadService(ctx, bookInfo)
            }
        }
        factory.close()
    }

    @JvmStatic
    fun scheduleActions(ctx: Context) {
        if (VOTDUtils.isVOTDTrulyEnabled(ctx)) {
            VOTDUtils.enableVOTDReminder(ctx)
        }
    }

    /**
     * Checks if there has been changes in the app resources on the server.
     * If there has been changes, then the upcoming versions from the remote config will be greater than that of locally saved.
     * */
    @JvmStatic
    fun checkForResourcesVersions(ctx: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val (urlsVersion, translationsVersion, recitationsVersion, tafsirsVersion)
                        = RetrofitInstance.github.getResourcesVersions()

                val localUrlsVersion = SPAppConfigs.getUrlsVersion(ctx)
                val localTranslationsVersion = SPAppConfigs.getTranslationsVersion(ctx)
                val localRecitationsVersion = SPAppConfigs.getRecitationsVersion(ctx)
                val localTafsirsVersion = SPAppConfigs.getTafsirsVersion(ctx)

                if (urlsVersion > localUrlsVersion) {
                    SPAppActions.setFetchUrlsForce(ctx, true)
                    SPAppConfigs.setUrlsVersion(ctx, urlsVersion)
                }

                if (translationsVersion > localTranslationsVersion) {
                    SPAppActions.setFetchTranslationsForce(ctx, true)
                    SPAppConfigs.setTranslationsVersion(ctx, translationsVersion)
                }

                if (recitationsVersion > localRecitationsVersion) {
                    SPAppActions.setFetchRecitationsForce(ctx, true)
                    SPAppConfigs.setRecitationsVersion(ctx, recitationsVersion)
                }

                if (tafsirsVersion > localTafsirsVersion) {
                    SPAppActions.setFetchTafsirsForce(ctx, true)
                    SPAppConfigs.setTafsirsVersion(ctx, tafsirsVersion)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @JvmStatic
    fun checkForCrashLogs(ctx: Context) {
        val lastCrashLog = SPLog.getLastCrashLog(ctx)

        if (lastCrashLog.isNullOrEmpty()) return

        PeaceDialog.newBuilder(ctx)
            .setTitle(R.string.lastCrashLog)
            .setMessage(lastCrashLog)
            .setNeutralButton(R.string.strLabelCopy) { _, _ ->
                ctx.copyToClipboard(lastCrashLog)
                Toast.makeText(ctx, R.string.copiedToClipboard, Toast.LENGTH_LONG).show()
                SPLog.removeLastCrashLog(ctx)
            }
            .setPositiveButton(R.string.createIssue) { _, _ ->
                ctx.copyToClipboard(lastCrashLog)
                SPLog.removeLastCrashLog(ctx)
                // redirect to github issues
                AppBridge.newOpener(ctx).browseLink(ApiConfig.BUG_REPORT_URL)
                Toast.makeText(ctx, R.string.pasteCrashLogGithubIssue, Toast.LENGTH_LONG).show()
            }
            .show()
    }
}