package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.travel_diary.data.auth.datastore.AuthDataStore
import com.strayalphaca.travel_diary.data.auth.repository.AuthRepositoryImpl
import com.strayalphaca.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        @ReissueClient retrofit : Retrofit,
        authDataStore: AuthDataStore
    ) : AuthRepository {
        return AuthRepositoryImpl(retrofit, authDataStore)
    }

    @Singleton
    @Provides
    fun provideAuthDataStore(
        @ApplicationContext context : Context
    ) : AuthDataStore {
        return AuthDataStore(context.dataStore)
    }

}