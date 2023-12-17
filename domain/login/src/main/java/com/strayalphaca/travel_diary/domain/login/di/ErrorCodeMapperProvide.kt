package com.strayalphaca.travel_diary.domain.login.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginErrorCodeMapperProvide

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SignupErrorCodeMapperProvide

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthCodeErrorCodeMapperProvide
