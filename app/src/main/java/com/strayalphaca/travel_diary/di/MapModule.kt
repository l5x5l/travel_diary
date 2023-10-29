package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.map.data_store.MapDataStore
import com.strayalphaca.travel_diary.data.map.data_store.MapDataStoreImpl
import com.strayalphaca.travel_diary.data.map.repository.RemoteMapRepository
import com.strayalphaca.travel_diary.data.map.repository.TestMapRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.map.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {
    @Provides
    fun provideMapRepository(
        mapDataStore: MapDataStore,
        @BaseClient retrofit: Retrofit,
        authRepository: AuthRepository
    ) : MapRepository {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            RemoteMapRepository(retrofit, mapDataStore)
        } else {
            TestMapRepository(mapDataStore)
        }
    }

    @Provides
    @Singleton
    fun provideMapDataStore() : MapDataStore = MapDataStoreImpl()
}