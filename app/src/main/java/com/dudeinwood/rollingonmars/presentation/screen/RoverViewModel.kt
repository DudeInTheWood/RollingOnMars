package com.dudeinwood.rollingonmars.presentation.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.domain.usecase.MoveRoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoverViewModel @Inject constructor(
    private val moveRoverUseCase: MoveRoverUseCase
) : ViewModel() {

    private val _roverState = mutableStateOf<Rover?>(null)
    val roverState: State<Rover?> = _roverState

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    var savedCommand = ""

    fun moveRover(gridSize: String, obstacles: String, commands: String) {
        try {
            val (width, height) = gridSize.split(",").map { it.trim().toInt() }
            val grid = Grid(width, height)

            val parsedObstacles = obstacles.split(";").mapNotNull {
                val parts = it.trim().removeSurrounding("[", "]").split(",")
                if (parts.size == 2) Obstacle(parts[0].toInt(), parts[1].toInt()) else null
            }

            savedCommand= commands.uppercase()

            val result = moveRoverUseCase(savedCommand, grid, parsedObstacles)
            result.onSuccess {
                _roverState.value = it
                _errorMessage.value = null
            }.onFailure {
                _errorMessage.value = it.message
            }

        } catch (e: Exception) {
            _errorMessage.value = "Invalid input format"
        }
    }
}