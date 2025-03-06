package com.dudeinwood.rollingonmars.di

import com.dudeinwood.rollingonmars.data.repository.RoverRepositoryImpl
import com.dudeinwood.rollingonmars.data.service.RoverService
import com.dudeinwood.rollingonmars.domain.repository.RoverRepository
import com.dudeinwood.rollingonmars.domain.usecase.MoveRoverUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRoverService(): RoverService {
        return RoverService()
    }

    @Provides
    fun provideMarsRoverRepository(roverService: RoverService): RoverRepository {
        return RoverRepositoryImpl(roverService)
    }

    @Provides
    fun provideMoveRoverUseCase(service: RoverService): MoveRoverUseCase {
        return MoveRoverUseCase(service)
    }
}