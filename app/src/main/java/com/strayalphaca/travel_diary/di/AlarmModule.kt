package com.strayalphaca.travel_diary.di

import android.app.Activity
import com.strayalphaca.travel_diary.RootActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {
    @Provides
    fun provideRootActivityClassType() : KClass<out Activity> = RootActivity::class
}