package com.strayalphaca.travel_diary.di.core

import android.content.Context
import com.strayalphaca.travel_diary.core.data.room.database.TrailyRoomDatabase
import com.strayalphaca.travel_diary.core.data.room.entity.LocationEntity
import com.strayalphaca.travel_diary.map.model.City
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideTrailyRoomDatabase(@ApplicationContext context : Context) : TrailyRoomDatabase {
        return TrailyRoomDatabase.initLocationDataAndGetInstance(
            context,
            City.listOfCity().mapNotNull { city ->
                city.objectInstance?.let { LocationEntity(it.id, it.provinceId, it.group, it.name) }
            }
        )
    }

    @Provides
    @Singleton
    fun provideRecordDao(database : TrailyRoomDatabase) = database.recordDao()
}