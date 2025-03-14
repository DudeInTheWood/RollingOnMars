package com.dudeinwood.rollingonmars.domain.roverHandler

import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover
import com.dudeinwood.rollingonmars.utils.enums.Command
import com.dudeinwood.rollingonmars.utils.enums.Direction
import com.dudeinwood.rollingonmars.utils.enums.Direction.Companion.directionFromChar
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException

class RoverHandler {
    internal fun turnLeft(direction: Direction): Char {
        return when (direction) {
            Direction.N -> Direction.W.value
            Direction.W -> Direction.S.value
            Direction.S -> Direction.E.value
            Direction.E -> Direction.N.value
        }
    }

    internal fun turnRight(direction: Direction): Char {
        return when (direction) {
            Direction.N -> Direction.E.value
            Direction.E -> Direction.S.value
            Direction.S -> Direction.W.value
            Direction.W -> Direction.N.value
        }
    }

    internal fun moveForward(rover: Rover, grid: Grid, obstacles: List<Obstacle>): Rover {
        var nextX = rover.x
        var nextY = rover.y

        when (directionFromChar(rover.direction)) {
            Direction.N -> nextY += 1
            Direction.E -> nextX += 1
            Direction.S -> nextY -= 1
            Direction.W -> nextX -= 1
        }

        checkOutOfBound(nextX, grid, nextY, rover)

        checkDetectObstacle(obstacles, nextX, nextY, rover)

        rover.x = nextX
        rover.y = nextY
        return rover.copy(x = nextX, y = nextY)
    }

    private fun checkDetectObstacle(
        obstacles: List<Obstacle>,
        nextX: Int,
        nextY: Int,
        rover: Rover
    ) {
        if ((0 to 0) in obstacles.map { it.x to it.y } || (nextX to nextY) in obstacles.map { it.x to it.y }) {
            throw ObstacleDetectedException(
                "Error: Obstacle detected at ($nextX, $nextY)!",
                rover = rover
            )
        }
    }

    private fun checkOutOfBound(
        nextX: Int,
        grid: Grid,
        nextY: Int,
        rover: Rover
    ) {
        if (nextX !in 0 until grid.width || nextY !in 0 until grid.height) {
            throw OutOfBoundsException("Error: Rover cannot move out of bounds!", rover = rover)
        }
    }

    fun executeCommands(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover> {
        var rover = Rover()

        for (command in commands) {
            when (command) {
                Command.L.value -> rover.direction = turnLeft(directionFromChar(rover.direction))
                Command.R.value -> rover.direction = turnRight(directionFromChar(rover.direction))
                Command.M.value -> rover = moveForward(rover, grid, obstacles)
                else -> return Result.failure(IllegalArgumentException("Invalid command: $command"))
            }
        }
        return Result.success(rover)
    }
}