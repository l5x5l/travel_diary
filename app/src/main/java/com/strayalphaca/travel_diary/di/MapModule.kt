package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.data.map.data_cache_store.MapDataCacheStore
import com.strayalphaca.travel_diary.data.map.data_cache_store.MapDataCacheStoreImpl
import com.strayalphaca.travel_diary.data.map.data_source.MapLocalDataSource
import com.strayalphaca.travel_diary.data.map.repository.LocalMapRepository
import com.strayalphaca.travel_diary.data.map.repository.RemoteMapRepository
import com.strayalphaca.travel_diary.data.map.repository.TestMapRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.map.repository.MapRepository
import com.strayalphaca.travel_diary.core.presentation.model.IS_LOCAL
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
        mapDataCacheStore: MapDataCacheStore,
        @BaseClient retrofit: Retrofit,
        authRepository: AuthRepository,
        demoDataSource: DemoDataSource,
        mapLocalDataSource: MapLocalDataSource
    ) : MapRepository {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            if (IS_LOCAL) {
                LocalMapRepository(mapLocalDataSource, mapDataCacheStore)
            } else {
                RemoteMapRepository(retrofit, mapDataCacheStore)
            }
        } else {
            TestMapRepository(mapDataCacheStore, demoDataSource)
        }
    }

    @Provides
    @Singleton
    fun provideMapDataStore() : MapDataCacheStore = MapDataCacheStoreImpl()
}