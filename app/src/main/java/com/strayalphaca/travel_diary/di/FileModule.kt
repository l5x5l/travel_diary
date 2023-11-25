package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.presentation.screens.diary.model.FileResizeHandlerImpl
import com.strayalphaca.travel_diary.data.file.repository.FileRepositoryImpl
import com.strayalphaca.travel_diary.data.file.repository.RemoteFileRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.file.model.FileResizeHandler
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideFileResizeHandler(
        @ApplicationContext context: Context
    ) : FileResizeHandler {
        return FileResizeHandlerImpl(context)
    }
}