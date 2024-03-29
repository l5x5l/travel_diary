package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.DefaultErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.login.AuthCodeErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.login.LoginErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.login.SignupErrorCodeMapper
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.data.login.data_source.LoginDataSource
import com.strayalphaca.travel_diary.data.login.data_source.LoginLocalDataSource
import com.strayalphaca.travel_diary.data.login.repository_impl.LoginRepositoryImpl
import com.strayalphaca.travel_diary.data.login.repository_impl.RemoteLoginRepository
import com.strayalphaca.travel_diary.domain.login.di.AuthCodeErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.di.LoginErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.di.SignupErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.di.WithdrawalErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalphaca.travel_diary.core.presentation.model.IS_LOCAL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    fun provideLoginRepository(
        @BaseClient baseRetrofit: Retrofit,
        @NoHeaderClient noHeaderRetrofit: Retrofit,
        loginDataSource: LoginDataSource
    ): LoginRepository {
        return if (IS_LOCAL) {
            LoginRepositoryImpl(loginDataSource)
        } else {
            return RemoteLoginRepository(baseRetrofit, noHeaderRetrofit)
        }
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        recordDao: RecordDao
    ) : LoginDataSource {
        return LoginLocalDataSource(recordDao)
    }

    @LoginErrorCodeMapperProvide
    @Provides
    fun provideLoginErrorCodeMapper(
        @ApplicationContext context : Context
    ) : ErrorCodeMapper {
        return DefaultErrorCodeMapper(LoginErrorCodeMapper(context), context)
    }

    @SignupErrorCodeMapperProvide
    @Provides
    fun provideSignupErrorCodeMapper(
        @ApplicationContext context : Context
    ) : ErrorCodeMapper {
        return DefaultErrorCodeMapper(SignupErrorCodeMapper(context), context)
    }

    @AuthCodeErrorCodeMapperProvide
    @Provides
    fun provideAuthCodeErrorCodeMapper(
        @ApplicationContext context : Context
    ) : ErrorCodeMapper {
        return DefaultErrorCodeMapper(AuthCodeErrorCodeMapper(context), context)
    }

    @WithdrawalErrorCodeMapperProvide
    @Provides
    fun provideWithdrawalErrorCodeMapper(
        @ApplicationContext context : Context
    ) : ErrorCodeMapper {
        return DefaultErrorCodeMapper(null, context)
    }
}