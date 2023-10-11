package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.map.data_store.MapDataStore
import com.strayalphaca.travel_diary.data.map.data_store.MapDataStoreImpl
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
    fun provideMapRepository(
        mapDataStore: MapDataStore
    ) : MapRepository = TestMapRepository(mapDataStore)

    @Provides
    @Singleton
    fun provideMapDataStore() : MapDataStore = MapDataStoreImpl()
}