package dev.lantt.itindr.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dev.lantt.itindr.core.constants.Constants.BASE_URL
import dev.lantt.itindr.core.constants.Constants.CONNECT_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.READ_TIMEOUT_SEC
import dev.lantt.itindr.core.constants.Constants.WRITE_TIMEOUT_SEC
import dev.lantt.itindr.core.data.api.AuthApiService
import dev.lantt.itindr.core.data.datasource.SessionManager
import dev.lantt.itindr.core.data.interceptor.AuthInterceptor
import dev.lantt.itindr.di.Labels.AUTH_OKHTTP
import dev.lantt.itindr.di.Labels.AUTH_RETROFIT
import dev.lantt.itindr.di.Labels.REGULAR_OKHTTP
import dev.lantt.itindr.di.Labels.REGULAR_RETROFIT
import dev.lantt.itindr.profile.data.api.ProfileApiService
import dev.lantt.itindr.profile.data.api.TopicApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
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

private fun provideAuthInterceptor(
    authApiService: AuthApiService,
    sessionManager: SessionManager
): AuthInterceptor =
    AuthInterceptor(authApiService, sessionManager)

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

private fun provideAuthOkHttpClient(
    authInterceptor: AuthInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
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

private fun provideProfileApiService(
    retrofit: Retrofit
): ProfileApiService =
    retrofit.create(ProfileApiService::class.java)

private fun provideTopicApiService(
    retrofit: Retrofit
): TopicApiService =
    retrofit.create(TopicApiService::class.java)

fun networkModule(): Module = module {
    singleOf(::provideLoggingInterceptor)
    singleOf(::provideAuthInterceptor)

    singleOf(::provideGson)
    singleOf(::provideSessionManager)

    singleOf(::provideOkHttpClient) {
        named(REGULAR_OKHTTP)
    }
    singleOf(::provideAuthOkHttpClient) {
        named(AUTH_OKHTTP)
    }

    single(named(REGULAR_RETROFIT)) {
        provideRetrofit(get(named(REGULAR_OKHTTP)), get())
    }
    single(named(AUTH_RETROFIT)) {
        provideRetrofit(get(named(AUTH_OKHTTP)), get())
    }

    single {
        provideAuthApiService(get(named(REGULAR_RETROFIT)))
    }
    single {
        provideProfileApiService(get(named(AUTH_RETROFIT)))
    }
    single {
        provideTopicApiService(get(named(AUTH_RETROFIT)))
    }
}