package com.goran.quranipiroz.components

import android.content.Context
import com.goran.quranipiroz.BuildConfig
import com.goran.quranipiroz.api.JsonHelper
import com.goran.quranipiroz.utils.univ.FileUtils
import kotlinx.serialization.decodeFromString

class AppUpdateInfo(private val ctx: Context) {
    companion object {
        const val CRITICAL = 5
        const val MAJOR = 4
        const val MODERATE = 3
        const val MINOR = 2
        const val COSMETIC = 1
        const val NONE = 0
    }

    private val updates: List<AppUpdate> = try {
        val fileUtils = FileUtils.newInstance(ctx)
        val updatesString = fileUtils.readFile(fileUtils.appUpdatesFile)
        JsonHelper.json.decodeFromString(updatesString)
    } catch (e: Exception) {
        ArrayList()
    }

    fun getMostImportantUpdate(): AppUpdate {
        val currentAppVersion = BuildConfig.VERSION_CODE

        val mostImportantUpdate = updates
            .filter { it.version > currentAppVersion }
            .sortedBy { it.priority }
        return mostImportantUpdate.takeIf { it.isNotEmpty() }?.get(0) ?: AppUpdate(0, NONE)
    }
}
