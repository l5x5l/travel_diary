package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.presentation.screens.diary.model.MusicPlayer
import com.strayalphaca.presentation.screens.diary.model.MusicPlayerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MusicPlayerModule {

    @Provides
    @ViewModelScoped
    fun provideMusicPlayer(@ApplicationContext context : Context) : MusicPlayer {
        return MusicPlayerImpl(context)
    }
}