package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.presentation.screens.diary.model.FileResizeHandlerImpl
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.data.file.repository.FileRepositoryImpl
import com.strayalphaca.travel_diary.data.file.repository.LocalFileRepository
import com.strayalphaca.travel_diary.data.file.repository.RemoteFileRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.file.model.FileResizeHandler
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import com.strayalphaca.travel_diary.mode.IS_LOCAL
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
        authRepository: AuthRepository,
        recordDao: RecordDao
    ) : FileRepository {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            if (IS_LOCAL) {
                LocalFileRepository(recordDao)
            } else {
                RemoteFileRepository(retrofit)
            }
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