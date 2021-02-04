package com.cgkim.image_search.di

import com.cgkim.image_search.data.ApiService
import com.cgkim.image_search.repo.Repository
import com.cgkim.image_search.repo.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(service: ApiService): Repository = RepositoryImpl(service)
}