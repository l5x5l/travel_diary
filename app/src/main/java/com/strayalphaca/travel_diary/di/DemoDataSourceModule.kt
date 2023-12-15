package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DemoDataSourceModule {
    @Singleton
    @Provides
    fun bindDemoDataSource() : DemoDataSource {
        return DemoDataSourceImpl()
    }
}