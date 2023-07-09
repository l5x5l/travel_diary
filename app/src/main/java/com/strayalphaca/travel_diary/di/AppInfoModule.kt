package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.data.app_info.data_source.AppInfoDataSource
import com.strayalphaca.data.app_info.data_source.AppInfoDataSourceImpl
import com.strayalphaca.travel_diary.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppInfoModule {

    @Provides
    @ViewModelScoped
    fun providerAppInfoDataSource(@ApplicationContext context : Context) : AppInfoDataSource {
        return AppInfoDataSourceImpl(context.dataStore)
    }
}