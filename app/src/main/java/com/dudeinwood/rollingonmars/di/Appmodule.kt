package com.dudeinwood.rollingonmars.di

import com.dudeinwood.rollingonmars.data.repository.RoverRepositoryImpl
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
    fun provideMarsRoverRepository(): RoverRepository = RoverRepositoryImpl()

    @Provides
    fun provideMoveRoverUseCase(repository: RoverRepository): MoveRoverUseCase {
        return MoveRoverUseCase(repository)
    }
}