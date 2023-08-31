package com.strayalphaca.travel_diary.di

import com.strayalphaca.data.auth.repository.AuthRepositoryImpl
import com.strayalphaca.domain.auth.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthRepository(@ReissueClient retrofit : Retrofit) : AuthRepository {
        return AuthRepositoryImpl(retrofit)
    }
}