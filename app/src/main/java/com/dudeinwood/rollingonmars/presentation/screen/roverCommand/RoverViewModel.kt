package com.dudeinwood.rollingonmars.presentation.screen.roverCommand

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.lifecycle.ViewModel
import com.dudeinwood.rollingonmars.R
import com.dudeinwood.rollingonmars.data.model.Grid
import com.dudeinwood.rollingonmars.data.model.Obstacle
import com.dudeinwood.rollingonmars.data.model.Rover
import com.dudeinwood.rollingonmars.domain.usecase.MoveRoverUseCase
import com.dudeinwood.rollingonmars.utils.exceptions.ObstacleDetectedException
import com.dudeinwood.rollingonmars.utils.exceptions.OutOfBoundsException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RoverViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moveRoverUseCase: MoveRoverUseCase
) : ViewModel() {
    private val _roverState = mutableStateOf<Rover?>(null)
    val roverState: State<Rover?> = _roverState

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    var savedGridSize = 0
    var savedCommand = ""
    var savedObstacle = listOf<Obstacle>()

    fun moveRover(gridSize: String, obstacles: String, commands: String) {
        try {
            val parseGrid = gridSize.toInt()
            val grid = Grid(parseGrid, parseGrid)

            val parsedObstacles = obstacles.split(";").mapNotNull {
                val parts = it.trim().removeSurrounding("[", "]").split(",")
                if (parts.size == 2) Obstacle(parts[0].toInt(), parts[1].toInt()) else null
            }

            //save data
            savedCommand = commands.uppercase()
            savedGridSize = parseGrid
            savedObstacle = parsedObstacles

            val result = moveRoverUseCase(savedCommand, grid, parsedObstacles)
            result.onSuccess {
                _roverState.value = it
                _errorMessage.value = null
            }.onFailure { error ->
                when (error) {
                    is ObstacleDetectedException -> {
                        _errorMessage.value =
                            context.getString(R.string.status_encountered_Obstacle)
                        _roverState.value = error.rover
                    }

                    is OutOfBoundsException -> {
                        _errorMessage.value = context.getString(R.string.status_out_of_bound)
                        _roverState.value = error.rover
                    }

                    else -> {
                        _errorMessage.value = context.getString(R.string.error_invalid_input)
                    }
                }
            }
        } catch (e: Exception) {
            _errorMessage.value = context.getString(R.string.error_invalid_input)
        }
    }
}