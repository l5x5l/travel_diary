package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarTestDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.RemoteCalendarDataSource
import com.strayalphaca.travel_diary.data.calendar.data_cache_store.CalendarDataCacheStore
import com.strayalphaca.travel_diary.data.calendar.data_cache_store.CalendarDataCacheStoreImpl
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarLocalDataSource
import com.strayalphaca.travel_diary.data.calendar.repository_impl.CalendarRepositoryImpl
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import com.strayalphaca.travel_diary.core.presentation.model.IS_LOCAL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarModule {
    @Binds
    abstract fun bindCalendarRepository(calendarRepository: CalendarRepositoryImpl) : CalendarRepository

    @Binds
    abstract fun bindCalendarDataStore(calendarDataStore: CalendarDataCacheStoreImpl) : CalendarDataCacheStore
}

@Module
@InstallIn(SingletonComponent::class)
object CalendarProvideModule {
    @Provides
    fun provideCalendarDataSource(
        @BaseClient retrofit : Retrofit,
        authRepository: AuthRepository,
        demoDataSource: DemoDataSource,
        recordDao: RecordDao
    ) : CalendarDataSource {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            if (IS_LOCAL) {
                CalendarLocalDataSource(recordDao)
            } else {
                RemoteCalendarDataSource(retrofit)
            }
        } else {
            CalendarTestDataSource(demoDataSource)
        }
    }
}