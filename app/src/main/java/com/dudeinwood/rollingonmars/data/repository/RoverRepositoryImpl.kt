package com.dudeinwood.rollingonmars.data.repository

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.data.service.RoverService
import com.dudeinwood.rollingonmars.domain.repository.RoverRepository
import com.dudeinwood.rollingonmars.utils.enum.Command
import com.dudeinwood.rollingonmars.utils.enum.Direction.Companion.directionFromChar
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(
    private val roverService: RoverService
) : RoverRepository {
    override fun moveRover(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover> {
        var rover = Rover()

        for (command in commands) {
            when (command) {
                Command.L.value -> rover.direction = roverService.turnLeft(directionFromChar(rover.direction))
                Command.R.value -> rover.direction = roverService.turnRight(directionFromChar(rover.direction))
                Command.M.value -> rover = roverService.moveForward(rover, grid, obstacles)
                else -> return Result.failure(IllegalArgumentException("Invalid command: $command"))
            }
        }
        return Result.success(rover)
    }
}