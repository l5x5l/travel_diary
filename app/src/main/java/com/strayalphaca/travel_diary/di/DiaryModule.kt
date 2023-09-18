package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.diary.data_source.DiaryDataSource
import com.strayalphaca.travel_diary.data.diary.data_source.DiaryTestDataSource
import com.strayalphaca.travel_diary.data.diary.repository_impl.DiaryRepositoryImpl
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
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