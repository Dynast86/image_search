package com.cgkim.image_search.di

import com.cgkim.image_search.data.ApiService
import com.cgkim.image_search.repo.Repository
import com.cgkim.image_search.repo.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(service: ApiService): Repository = RepositoryImpl(service)
}