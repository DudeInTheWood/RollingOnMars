package com.dudeinwood.rollingonmars.di

import com.dudeinwood.rollingonmars.domain.roverHandler.RoverHandler
import com.dudeinwood.rollingonmars.domain.usecase.MoveRoverUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRoverService(): RoverHandler {
        return RoverHandler()
    }

    @Provides
    fun provideMoveRoverUseCase(service: RoverHandler): MoveRoverUseCase {
        return MoveRoverUseCase(service)
    }
}