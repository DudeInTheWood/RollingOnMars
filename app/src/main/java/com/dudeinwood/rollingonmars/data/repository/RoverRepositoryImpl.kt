package com.dudeinwood.rollingonmars.data.repository

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.domain.repository.RoverRepository
import com.dudeinwood.rollingonmars.utils.enum.Command
import com.dudeinwood.rollingonmars.utils.enum.Direction
import com.dudeinwood.rollingonmars.utils.enum.Direction.Companion.directionFromChar
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
                    Command.L.value -> rover.direction = turnLeft(directionFromChar(rover.direction))
                    Command.R.value -> rover.direction = turnRight(directionFromChar(rover.direction))
                    Command.M.value -> moveForward(rover, grid, obstacles)
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

    fun turnLeft(direction: Direction): Char {
        return when (direction) {
            Direction.N -> Direction.W.value
            Direction.W -> Direction.S.value
            Direction.S -> Direction.E.value
            Direction.E -> Direction.N.value
        }
    }

    fun turnRight(direction: Direction): Char {
        return when (direction) {
            Direction.N -> Direction.E.value
            Direction.E -> Direction.S.value
            Direction.S -> Direction.W.value
            Direction.W -> Direction.N.value
        }
    }

    private fun moveForward(rover: Rover, grid: Grid, obstacles: List<Obstacle>) {
        var nextX = rover.x
        var nextY = rover.y

        when (directionFromChar(rover.direction)) {
            Direction.N -> nextY += 1
            Direction.E -> nextX += 1
            Direction.S -> nextY -= 1
            Direction.W -> nextX -= 1
        }

        if (nextX !in 0 until grid.width || nextY !in 0 until grid.height) {
            throw OutOfBoundsException("Error: Rover cannot move out of bounds!")
        }

        if (obstacles.any { it.x == nextX && it.y == nextY }) {
            throw ObstacleDetectedException("Error: Obstacle detected at ($nextX, $nextY)!")
        }

        rover.x = nextX
        rover.y = nextY
        rover.copy(x = nextX, y = nextY)
    }
}