package com.contacts.mazaady.authentication.presentation.di

import com.contacts.mazaady.authentication.data.datasource.AuthorizationService
import com.contacts.mazaady.authentication.data.repositoryimb.AuthorizationRepositoryImpl
import com.contacts.mazaady.authentication.domain.repository.AuthorizationRepository
import com.contacts.mazaady.core.data.module.NetworkModule
import com.contacts.mazaady.core.data.utils.EmittingResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit) : AuthorizationService {
        return retrofit.create(AuthorizationService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: AuthorizationService, emittingResponse: EmittingResponse) : AuthorizationRepository {
        return AuthorizationRepositoryImpl(loginService,emittingResponse)
    }


}