package com.strayalphaca.travel_diary.di

import android.app.Activity
import android.content.Context
import com.strayalphaca.travel_diary.RootActivity
import com.strayalphaca.travel_diary.data.alarm.datastore.AlarmDataStore
import com.strayalphaca.travel_diary.data.alarm.repository.AlarmRepositoryImpl
import com.strayalphaca.travel_diary.dataStore
import com.strayalphaca.travel_diary.domain.alarm.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {
    @Provides
    fun provideRootActivityClassType() : KClass<out Activity> = RootActivity::class

    @Singleton
    @Provides
    fun provideAlarmRepository(
        alarmDataStore: AlarmDataStore
    ) : AlarmRepository {
        return AlarmRepositoryImpl(alarmDataStore)
    }

    @Singleton
    @Provides
    fun provideAlarmDataStore(
        @ApplicationContext context : Context
    ) : AlarmDataStore {
        return AlarmDataStore(context.dataStore)
    }
}