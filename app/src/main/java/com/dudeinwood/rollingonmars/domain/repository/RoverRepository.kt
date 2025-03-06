package com.dudeinwood.rollingonmars.domain.repository

import com.dudeinwood.rollingonmars.domain.model.Grid
import com.dudeinwood.rollingonmars.domain.model.Obstacle
import com.dudeinwood.rollingonmars.domain.model.Rover

interface RoverRepository {
    fun moveRover(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover>
}