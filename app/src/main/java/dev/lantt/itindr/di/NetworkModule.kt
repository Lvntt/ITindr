package dev.lantt.itindr.di

import dev.lantt.itindr.auth.data.api.AuthApiService
import dev.lantt.itindr.core.constants.Constants.BASE_URL
import dev.lantt.itindr.core.constants.Constants.CONNECT_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.READ_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.WRITE_TIMEOUT_SEC
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

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
    okHttpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun provideAuthApiService(
    retrofit: Retrofit
): AuthApiService =
    retrofit.create(AuthApiService::class.java)

fun networkModule(): Module = module {
    singleOf(::provideLoggingInterceptor)
    singleOf(::provideOkHttpClient)
    singleOf(::provideRetrofit)

    singleOf(::provideAuthApiService)
}