package com.strayalphaca.travel_diary.di

import com.strayalphaca.travel_diary.data.login.data_source.LoginDataSource
import com.strayalphaca.travel_diary.data.login.data_source.LoginTestDataSource
import com.strayalphaca.travel_diary.data.login.repository_impl.RemoteLoginRepository
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    fun provideLoginRepository(@BaseClient retrofit: Retrofit) : LoginRepository  {
        return RemoteLoginRepository(retrofit)
    }

    @Provides
    fun provideLoginDataSource() : LoginDataSource {
        return LoginTestDataSource()
    }
}