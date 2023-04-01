package com.goran.quranipiroz.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.goran.quranipiroz.utils.Log
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
object RetrofitInstance {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            Log.d(chain.request().url())
            return@addInterceptor chain.proceed(chain.request())
        }
        .build()

    val github: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.WEBSITE_ROOT_URL)
            .addConverterFactory(
                JsonHelper.json.asConverterFactory(MediaType.get("application/json"))
            )
//            .client(client)
            .build()
            .create(GithubApi::class.java)
    }

    val quran: QuranApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.QURAN_API_ROOT_URL)
            .addConverterFactory(
                JsonHelper.json.asConverterFactory(MediaType.get("application/json"))
            )
//            .client(client)
            .build()
            .create(QuranApi::class.java)
    }
}
