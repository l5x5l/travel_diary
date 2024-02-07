package com.strayalphaca.travel_diary.di

import android.content.Context
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.DefaultErrorCodeMapper
import com.strayalphaca.presentation.models.error_code_mapper.diary.DiaryErrorCodeMapper
import com.strayalphaca.travel_diary.data.diary.data_source.DiaryDataSource
import com.strayalphaca.travel_diary.data.diary.data_source.DiaryLocalDataSource
import com.strayalphaca.travel_diary.data.diary.data_source.DiaryTestDataSource
import com.strayalphaca.travel_diary.data.diary.repository_impl.DiaryRepositoryImpl
import com.strayalphaca.travel_diary.data.diary.repository_impl.LocalDiaryRepository
import com.strayalphaca.travel_diary.data.diary.repository_impl.RemoteDiaryRepository
import com.strayalphaca.travel_diary.diary.di.DiaryErrorCodeMapperProvide
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.travel_diary.core.presentation.model.IS_LOCAL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiaryModule {
    @Binds
    abstract fun bindDiaryDataSource(diaryDataSource: DiaryTestDataSource) : DiaryDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DiaryProvideModule {

    @Provides
    fun provideDiaryRepository(
        authRepository: AuthRepository,
        localDairyRepository: LocalDiaryRepository,
        remoteDiaryRepository: RemoteDiaryRepository,
        demoDiaryRepository : DiaryRepositoryImpl
    ) : DiaryRepository {
        val hasToken = authRepository.getAccessToken() != null
        return if (hasToken) {
            if (IS_LOCAL) {
                localDairyRepository
            } else {
                remoteDiaryRepository
            }
        } else {
            demoDiaryRepository
        }
    }

    @Singleton
    @Provides
    fun provideRemoteDiaryRepository(
        @BaseClient retrofit : Retrofit,
    ) : RemoteDiaryRepository {
        return RemoteDiaryRepository(retrofit)
    }

    @Singleton
    @Provides
    fun provideLocalDiaryRepository(
        diaryLocalDataSource: DiaryLocalDataSource,
    ) : LocalDiaryRepository {
        return LocalDiaryRepository(diaryLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideDemoDiaryRepository(
        diaryDataSource: DiaryTestDataSource,
    ) : DiaryRepositoryImpl {
        return DiaryRepositoryImpl(diaryDataSource)
    }

    @DiaryErrorCodeMapperProvide
    @Provides
    fun provideDiaryErrorCodeMapper(
        @ApplicationContext context : Context
    ) : ErrorCodeMapper {
        return DefaultErrorCodeMapper(DiaryErrorCodeMapper(context), context)
    }
}