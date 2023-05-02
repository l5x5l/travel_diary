package com.strayalphaca.travel_diary.di

import com.strayalphaca.data.lock.data_source.LockDataSource
import com.strayalphaca.data.lock.data_source.LockTestDataSource
import com.strayalphaca.data.lock.repository_impl.LockRepositoryImpl
import com.strayalphaca.domain.lock.repository.LockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LockModule {
    @Binds
    abstract fun bindLockRepository(lockRepository : LockRepositoryImpl) : LockRepository

    @Binds
    abstract fun bindLockDataSource(lockDataSource : LockTestDataSource) : LockDataSource
}