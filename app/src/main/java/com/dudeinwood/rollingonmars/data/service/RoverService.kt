package com.dudeinwood.rollingonmars.data.service

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.utils.enum.Direction
import com.dudeinwood.rollingonmars.utils.enum.Direction.Companion.directionFromChar
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException

class RoverService {
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

    fun moveForward(rover: Rover, grid: Grid, obstacles: List<Obstacle>): Rover {
        var nextX = rover.x
        var nextY = rover.y

        when (directionFromChar(rover.direction)) {
            Direction.N -> nextY += 1
            Direction.E -> nextX += 1
            Direction.S -> nextY -= 1
            Direction.W -> nextX -= 1
        }

        if (nextX !in 0 until grid.width || nextY !in 0 until grid.height) {
            throw OutOfBoundsException("Error: Rover cannot move out of bounds!", rover = rover)
        }

        if (obstacles.any { it.x == 0 && it.y == 0 }) {
            throw ObstacleDetectedException("Error: Obstacle detected at ($nextX, $nextY)!", rover = rover)
        }

        if (obstacles.any { it.x == nextX && it.y == nextY }) {
            throw ObstacleDetectedException("Error: Obstacle detected at ($nextX, $nextY)!", rover = rover)
        }

        rover.x = nextX
        rover.y = nextY
        return rover.copy(x = nextX, y = nextY)
    }
}