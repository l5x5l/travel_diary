package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.file.repository.FileRepositoryImpl
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FileModule {
    @Provides
    fun provideFileRepository() : FileRepository = FileRepositoryImpl()
}