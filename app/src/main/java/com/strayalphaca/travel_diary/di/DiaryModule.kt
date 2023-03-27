package com.strayalphaca.travel_diary.di

import com.strayalphaca.data.diary.data_source.DiaryDataSource
import com.strayalphaca.data.diary.data_source.DiaryTestDataSource
import com.strayalphaca.data.diary.repository_impl.DiaryRepositoryImpl
import com.strayalphaca.domain.diary.repository.DiaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DiaryModule {
    @Binds
    abstract fun bindDiaryRepository(diaryRepository: DiaryRepositoryImpl) : DiaryRepository

    @Binds
    abstract fun bindDiaryDataSource(diaryDataSource: DiaryTestDataSource) : DiaryDataSource
}