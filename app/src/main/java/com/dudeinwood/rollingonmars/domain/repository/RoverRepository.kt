package com.dudeinwood.rollingonmars.domain.repository

import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover

interface RoverRepository {
    fun moveRover(commands: String, grid: Grid, obstacles: List<Obstacle>): Result<Rover>
}