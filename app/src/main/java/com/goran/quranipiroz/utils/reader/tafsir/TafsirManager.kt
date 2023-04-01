package com.goran.quranipiroz.utils.reader.tafsir

import android.content.Context
import com.goran.quranipiroz.api.JsonHelper
import com.goran.quranipiroz.api.RetrofitInstance
import com.goran.quranipiroz.api.models.tafsir.AvailableTafsirsModel
import com.goran.quranipiroz.api.models.tafsir.TafsirInfoModel
import com.goran.quranipiroz.utils.sharedPrefs.SPAppActions
import com.goran.quranipiroz.utils.sharedPrefs.SPReader
import com.goran.quranipiroz.utils.univ.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import java.io.IOException

object TafsirManager {
    private var availableTafsirsModel: AvailableTafsirsModel? = null


    @JvmStatic
    fun prepare(
        ctx: Context,
        force: Boolean,
        readyCallback: () -> Unit
    ) {
        if (!force && availableTafsirsModel != null) {
            readyCallback()
            return
        }

        loadTafsirs(ctx, force) { availableTafsirsModel ->
            TafsirManager.availableTafsirsModel = availableTafsirsModel
            readyCallback()
        }
    }


    private fun loadTafsirs(
        ctx: Context,
        force: Boolean,
        callback: (AvailableTafsirsModel?) -> Unit
    ) {
        val fileUtils = FileUtils.newInstance(ctx)

        val tafsirsFile = fileUtils.tafsirsManifestFile
        if (force) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val stringData = RetrofitInstance.github.getAvailableTafsirs().string()

                    fileUtils.createFile(tafsirsFile)
                    fileUtils.writeToFile(tafsirsFile, stringData)

                    withContext(Dispatchers.Main) {
                        postRecitationsLoad(ctx, stringData, callback)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        callback(null)
                    }
                }
            }
        } else {
            if (!tafsirsFile.exists()) {
                loadTafsirs(ctx, true, callback)
                return
            }

            try {
                val stringData = fileUtils.readFile(tafsirsFile)
                if (stringData.isEmpty()) {
                    loadTafsirs(ctx, true, callback)
                    return
                }

                postRecitationsLoad(ctx, stringData, callback)
            } catch (e: IOException) {
                e.printStackTrace()
                loadTafsirs(ctx, true, callback)
            }
        }
    }

    private fun postRecitationsLoad(
        ctx: Context,
        stringData: String,
        callback: (AvailableTafsirsModel?) -> Unit
    ) {
        SPAppActions.setFetchTafsirsForce(ctx, false)
        val savedTafsirKey = SPReader.getSavedTafsirKey(ctx)

        try {
            val availableTafsirsModel = JsonHelper.json.decodeFromString<AvailableTafsirsModel>(
                stringData
            )

            availableTafsirsModel.tafsirs.values.forEach { tafsirModels ->
                tafsirModels.forEach { tafsirModel ->
                    tafsirModel.isChecked = tafsirModel.key == savedTafsirKey
                }

            }

            callback(availableTafsirsModel)
        } catch (e: Exception) {
            e.printStackTrace()
            callback(null)
        }
    }

    @JvmStatic
    fun getModel(key: String): TafsirInfoModel? {
        val tafsirListForLangCodes = availableTafsirsModel?.tafsirs?.values ?: return null

        for (tafsirList in tafsirListForLangCodes) {
            val tafsir = tafsirList.firstOrNull { it.key == key }
            if (tafsir != null) return tafsir
        }

        return null
    }

    @JvmStatic
    fun getModels(): Map<String, List<TafsirInfoModel>>? {
        return availableTafsirsModel?.tafsirs
    }


    @JvmStatic
    fun getModels(lang: String?): List<TafsirInfoModel>? {
        return availableTafsirsModel?.tafsirs?.get(lang!!)
    }


    @JvmStatic
    fun setSavedTafsirKey(key: String) {
        availableTafsirsModel?.tafsirs?.values?.forEach { tafsirModels ->
            tafsirModels.forEach { tafsirModel ->
                tafsirModel.isChecked = tafsirModel.key == key
            }
        }
    }
}