package io.github.vgupta98.compose_directions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.vgupta98.compose_directions.domain.DirectorProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDirector() = DirectorProvider.provide()
}