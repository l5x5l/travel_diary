package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarDataSource
import com.strayalphaca.travel_diary.data.calendar.data_source.CalendarTestDataSource
import com.strayalphaca.travel_diary.data.calendar.data_store.CalendarDataStore
import com.strayalphaca.travel_diary.data.calendar.data_store.CalendarDataStoreImpl
import com.strayalphaca.travel_diary.data.calendar.repository_impl.CalendarRepositoryImpl
import com.strayalphaca.travel_diary.domain.calendar.repository.CalendarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarModule {
    @Binds
    abstract fun bindCalendarRepository(calendarRepository: CalendarRepositoryImpl) : CalendarRepository

    @Binds
    abstract fun bindCalendarDataSource(calendarDataSource: CalendarTestDataSource) : CalendarDataSource

    @Binds
    abstract fun bindCalendarDataStore(calendarDataStore: CalendarDataStoreImpl) : CalendarDataStore
}