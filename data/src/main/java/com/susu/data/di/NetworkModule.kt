package com.susu.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.susu.data.Constants.RETROFIT_TAG
import com.susu.data.extension.isJsonArray
import com.susu.data.extension.isJsonObject
import com.susu.data.network.ApiService
import com.susu.data.network.AuthService
import com.susu.data.network.TokenAuthenticator
import com.susu.data.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.oksusu.site/api/v1/"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(
        json: Json,
    ): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        when {
            !message.isJsonObject() && !message.isJsonArray() ->
                Timber.tag(RETROFIT_TAG).d("CONNECTION INFO -> $message")

            else -> kotlin.runCatching {
                json.encodeToString(Json.parseToJsonElement(message))
            }.onSuccess {
                Timber.tag(RETROFIT_TAG).d(it)
            }.onFailure {
                Timber.tag(RETROFIT_TAG).d(message)
            }
        }
    }.apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @AuthOkHttpClient
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        json: Json,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(@AuthRetrofit retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
