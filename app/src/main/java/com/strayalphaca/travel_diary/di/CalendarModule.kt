package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarTestDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.RemoteCalendarDataSource
import com.strayalphaca.travel_diary.data.calendar.data_store.CalendarDataStore
import com.strayalphaca.travel_diary.data.calendar.data_store.CalendarDataStoreImpl
import com.strayalphaca.travel_diary.data.calendar.repository_impl.CalendarRepositoryImpl
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
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
    abstract fun bindCalendarDataStore(calendarDataStore: CalendarDataStoreImpl) : CalendarDataStore
}

@Module
@InstallIn(SingletonComponent::class)
object CalendarProvideModule {
    @Provides
    fun provideCalendarDataSource(
        @BaseClient retrofit : Retrofit,
        authRepository: AuthRepository,
        demoDataSource: DemoDataSource
    ) : CalendarDataSource {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            RemoteCalendarDataSource(retrofit)
        } else {
            CalendarTestDataSource(demoDataSource)
        }
    }
}