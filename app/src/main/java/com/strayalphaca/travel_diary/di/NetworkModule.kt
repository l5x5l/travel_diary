package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.BuildConfig
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.network.BaseInterceptor
import com.strayalphaca.travel_diary.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val base_url = BuildConfig.SERVER_URL
    private const val connect_timeout_milli = 10000L
    private const val write_timeout_milli = 10000L
    private const val read_timeout_milli = 5000L

    @Singleton
    @Provides
    fun provideOkhttp(
        authRepository: AuthRepository
    ) : OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(connect_timeout_milli, TimeUnit.MILLISECONDS)
            .writeTimeout(write_timeout_milli, TimeUnit.MILLISECONDS)
            .readTimeout(read_timeout_milli, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(
                setOf(), authRepository
            ))
            .addInterceptor(interceptor)
            .build()
    }

    @BaseClient
    @Singleton
    @Provides
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @ReissueClient
    @Singleton
    @Provides
    fun provideReissueRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(connect_timeout_milli, TimeUnit.MILLISECONDS)
            .writeTimeout(write_timeout_milli, TimeUnit.MILLISECONDS)
            .readTimeout(read_timeout_milli, TimeUnit.MILLISECONDS)
            .addInterceptor(BaseInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @NoHeaderClient
    @Singleton
    @Provides
    fun provideNoHeaderRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(connect_timeout_milli, TimeUnit.MILLISECONDS)
            .writeTimeout(write_timeout_milli, TimeUnit.MILLISECONDS)
            .readTimeout(read_timeout_milli, TimeUnit.MILLISECONDS)
            .addInterceptor(BaseInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReissueClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoHeaderClient