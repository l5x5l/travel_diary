package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.map.repository.TestMapRepository
import com.strayalphaca.travel_diary.map.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Provides
    @Singleton
    fun provideMapRepository() : MapRepository = TestMapRepository()
}