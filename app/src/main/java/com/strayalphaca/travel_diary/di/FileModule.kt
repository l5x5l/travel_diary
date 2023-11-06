package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.file.repository.FileRepositoryImpl
import com.strayalphaca.travel_diary.data.file.repository.RemoteFileRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object FileModule {
    @Provides
    fun provideFileRepository(
        @BaseClient retrofit: Retrofit,
        authRepository: AuthRepository
    ) : FileRepository {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            RemoteFileRepository(retrofit)
        } else {
            FileRepositoryImpl()
        }
    }
}