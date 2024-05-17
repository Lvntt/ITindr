package dev.lantt.itindr.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dev.lantt.itindr.core.data.api.AuthApiService
import dev.lantt.itindr.core.constants.Constants.BASE_URL
import dev.lantt.itindr.core.constants.Constants.CONNECT_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.READ_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.WRITE_TIMEOUT_SEC
import dev.lantt.itindr.core.data.datasource.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

private fun provideGson(): Gson =
    GsonBuilder()
        .registerTypeAdapter(
            Long::class.java, JsonDeserializer { json, _, _ ->
                ZonedDateTime
                    .parse(json.asString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    .toInstant()
                    .toEpochMilli()
            }
        )
        .create()

private fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_SEC, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS)
        .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

private fun provideSessionManager(
    context: Context
): SessionManager = SessionManager(context)

private fun provideAuthApiService(
    retrofit: Retrofit
): AuthApiService =
    retrofit.create(AuthApiService::class.java)

fun networkModule(): Module = module {
    singleOf(::provideLoggingInterceptor)
    singleOf(::provideGson)
    singleOf(::provideOkHttpClient)
    singleOf(::provideRetrofit)
    singleOf(::provideSessionManager)

    singleOf(::provideAuthApiService)
}