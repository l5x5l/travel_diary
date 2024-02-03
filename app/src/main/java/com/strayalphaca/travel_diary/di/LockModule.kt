package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalphaca.travel_diary.data.lock.data_source.LockDataSource
import com.strayalphaca.travel_diary.data.lock.repository_impl.LockRepositoryImpl
import com.strayalpaca.travel_diary.domain.lock.repository.LockRepository
import com.strayalphaca.travel_diary.data.lock.data_source.LockDataStore
import com.strayalphaca.travel_diary.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LockModule {
    @Singleton
    @Provides
    fun provideLockRepository(lockDataSource: LockDataSource) : LockRepository {
        return LockRepositoryImpl(lockDataSource)
    }

    @Singleton
    @Provides
    fun provideLockDataSource(
        @ApplicationContext context : Context
    ) : LockDataSource {
        return LockDataStore(context.dataStore)
    }
}
