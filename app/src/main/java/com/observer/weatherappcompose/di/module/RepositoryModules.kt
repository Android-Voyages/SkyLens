package com.observer.weatherappcompose.di.module

import com.observer.weatherappcompose.data.repositoryImpl.network.NetWorkRepositoryImpl
import com.observer.weatherappcompose.domain.repository.NetWorkRepositry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideNetworkRepositoryImpl(repository: NetWorkRepositoryImpl): NetWorkRepositry
}
