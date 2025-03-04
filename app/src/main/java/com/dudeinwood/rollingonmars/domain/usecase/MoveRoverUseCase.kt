package com.dudeinwood.rollingonmars.domain.usecase

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.domain.repository.RoverRepository
import javax.inject.Inject

class MoveRoverUseCase @Inject constructor(
    private val repository: RoverRepository
) {
    operator fun invoke(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover> {
        return repository.moveRover(commands, grid, obstacles)
    }
}