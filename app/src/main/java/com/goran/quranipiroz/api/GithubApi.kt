package com.goran.quranipiroz.api

import com.goran.quranipiroz.api.models.AppUpdate
import com.goran.quranipiroz.api.models.AppUrls
import com.goran.quranipiroz.api.models.ResourcesVersions
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("inventory/versions/app_updates.json")
    suspend fun getAppUpdates(): List<AppUpdate>

    @GET("inventory/versions/resources_versions.json")
    suspend fun getResourcesVersions(): ResourcesVersions

    @GET("inventory/other/urls.json")
    suspend fun getAppUrls(): AppUrls

    @GET("inventory/translations/available_translations_info.json")
    suspend fun getAvailableTranslations(): ResponseBody

    @GET("{path}")
    suspend fun getTranslation(@Path("path") path: String): ResponseBody

    @GET("inventory/quran_scripts/{filename}")
    suspend fun getQuranScript(@Path("filename") filename: String): ResponseBody

    @GET("inventory/fonts/{scriptKey}/{filename}")
    suspend fun getKFQPCFont(
        @Path("scriptKey") scriptKey: String,
        @Path("filename") filename: String
    ): ResponseBody

    @GET("inventory/recitations/available_recitations_info.json")
    suspend fun getAvailableRecitations(): ResponseBody

    @GET("inventory/tafsirs/available_tafsirs_info.json")
    suspend fun getAvailableTafsirs(): ResponseBody
}
