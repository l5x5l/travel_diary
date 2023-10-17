package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkhttp(
        authRepository: AuthRepository
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(5000L, TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor(
                setOf(), authRepository
            ))
            .build()
    }

    @BaseClient
    @Singleton
    @Provides
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("base_url")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @ReissueClient
    @Singleton
    @Provides
    fun provideReissueRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(5000L, TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("base_url")
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