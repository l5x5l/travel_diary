package com.strayalphaca.travel_diary.di

import com.strayalphaca.data.login.data_source.LoginDataSource
import com.strayalphaca.data.login.data_source.LoginTestDataSource
import com.strayalphaca.data.login.repository_impl.LoginRepositoryImpl
import com.strayalphaca.domain.login.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(loginRepository: LoginRepositoryImpl) : LoginRepository

    @Binds
    abstract fun bindLoginDataSource(loginDataSource: LoginTestDataSource) : LoginDataSource
}