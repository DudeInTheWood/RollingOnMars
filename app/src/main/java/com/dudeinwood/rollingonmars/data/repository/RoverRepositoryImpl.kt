package com.dudeinwood.rollingonmars.data.repository

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.domain.repository.RoverRepository
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import java.lang.Exception
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor() : RoverRepository {
    override fun moveRover(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover> {
        return try {
            val rover = Rover()

            for (command in commands) {
                when (command) {
                    'L' -> rover.direction = turnLeft(rover.direction)
                    'R' -> rover.direction = turnRight(rover.direction)
                    'M' -> moveForward(rover, grid, obstacles)
                    else -> return Result.failure(IllegalArgumentException("Invalid command: $command"))
                }
            }
            Result.success(rover)
        } catch (e: Exception) {
            Result.failure(e)
        } catch (e: OutOfBoundsException) {
            Result.failure(e)
        } catch (e: ObstacleDetectedException) {
            Result.failure(e)
        }
    }

    private fun turnLeft(direction: Char): Char {
        return when (direction) {
            'N' -> 'W'
            'W' -> 'S'
            'S' -> 'E'
            'E' -> 'N'
            else -> direction
        }
    }

    private fun turnRight(direction: Char): Char {
        return when (direction) {
            'N' -> 'E'
            'E' -> 'S'
            'S' -> 'W'
            'W' -> 'N'
            else -> direction
        }
    }

    private fun moveForward(rover: Rover, grid: Grid, obstacles: List<Obstacle>) {
        var nextX = rover.x
        var nextY = rover.y

        when (rover.direction) {
            'N' -> nextY += 1
            'E' -> nextX += 1
            'S' -> nextY -= 1
            'W' -> nextX -= 1
        }

        if (nextX !in 0 until grid.width || nextY !in 0 until grid.height) {
            throw OutOfBoundsException("Error: Rover cannot move out of bounds!")
        }

        if (obstacles.any { it.x == nextX && it.y == nextY }) {
            throw ObstacleDetectedException("Error: Obstacle detected at ($nextX, $nextY)!")
        }

        rover.x = nextX
        rover.y = nextY
    }
}