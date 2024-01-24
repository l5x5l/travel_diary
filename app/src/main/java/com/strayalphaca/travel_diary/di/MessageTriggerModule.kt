package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.core.presentation.model.MessageTrigger
import com.strayalphaca.travel_diary.core.presentation.model.GlobalMessageTrigger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageTriggerModule {
    @Singleton
    @Provides
    fun provideMessageTrigger() : MessageTrigger {
        return GlobalMessageTrigger()
    }
}