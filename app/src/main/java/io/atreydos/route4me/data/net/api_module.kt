package io.atreydos.route4me.data.net

import com.google.gson.GsonBuilder
import io.atreydos.route4me.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    converterFactory: Converter.Factory,
    baseUrl: String
): T {

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(T::class.java)
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG)
            level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create(
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create()
    )
}
