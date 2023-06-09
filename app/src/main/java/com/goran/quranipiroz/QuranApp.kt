package com.goran.quranipiroz

import android.app.Application
import android.content.Context
import android.os.Build
import android.webkit.WebView
import androidx.appcompat.app.AppCompatDelegate
import com.goran.quranipiroz.utils.app.NotificationUtils
import com.goran.quranipiroz.utils.app.ThemeUtils
import com.goran.quranipiroz.utils.exceptions.CustomExceptionHandler

class QuranApp : Application() {
    override fun attachBaseContext(base: Context) {
        initBeforeBaseAttach(base)
        super.attachBaseContext(base)
    }

    private fun initBeforeBaseAttach(base: Context) {
        updateTheme(base)
    }

    private fun updateTheme(base: Context) {
        AppCompatDelegate.setDefaultNightMode(ThemeUtils.resolveThemeModeFromSP(base))
    }

    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createNotificationChannels(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val process = getProcessName()
            if (packageName != process) WebView.setDataDirectorySuffix(process)
        }

        // Handler for uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler(this))
    }
}
