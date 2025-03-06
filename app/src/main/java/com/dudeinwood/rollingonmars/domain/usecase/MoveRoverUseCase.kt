package com.dudeinwood.rollingonmars.domain.usecase

import com.dudeinwood.rollingonmars.domain.roverHandler.RoverHandler
import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import javax.inject.Inject

class MoveRoverUseCase @Inject constructor(
    private val roverHandler: RoverHandler
) {
    operator fun invoke(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover> {
        return try {
            roverHandler.executeCommands(commands, grid, obstacles)
        } catch (e: OutOfBoundsException) {
            Result.failure(e)
        } catch (e: ObstacleDetectedException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}