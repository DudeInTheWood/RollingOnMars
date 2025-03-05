package com.dudeinwood.rollingonmars.presentation.screen.roverResult

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dudeinwood.rollingonmars.R
import com.dudeinwood.rollingonmars.presentation.component.RoverStatusView
import com.dudeinwood.rollingonmars.presentation.screen.roverCommand.RoverViewModel
import com.dudeinwood.rollingonmars.presentation.screen.roverResult.animation.animateCommands
import com.dudeinwood.rollingonmars.utils.enum.Direction
import kotlinx.coroutines.delay

@Composable
fun RoverResultScreen(viewModel: RoverViewModel = hiltViewModel()) {
    val rover = viewModel.roverState.value

    val gridSize = viewModel.savedGridSize
    val cellSize = 100f
    val density = LocalDensity.current
    val gridSizeDp = with(density) { (gridSize * cellSize).toDp() }

    var animatedX by remember { mutableFloatStateOf(0f) }
    var animatedY by remember { mutableFloatStateOf(0f) }
    var animatedRotation by remember { mutableFloatStateOf(0f) }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(rover) {
            rover?.let {
                animateCommands(
                    viewModel.savedCommand,
                    0,
                    0,
                    Direction.N.value
                ) { newX, newY, newRotation ->
                    animatedX = newX
                    animatedY = newY
                    animatedRotation = newRotation
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            rover?.let {
                RoverStatusView(
                    rover = rover,
                    statusMessage = stringResource(R.string.status_success)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Canvas(modifier = Modifier.size(gridSizeDp)) {
                // Draw grid
                for (i in 0 until gridSize) {
                    for (j in 0 until gridSize) {
                        drawRect(
                            color = Color.Gray,
                            topLeft = Offset(i * cellSize, j * cellSize),
                            size = Size(cellSize, cellSize),
                            style = Stroke(width = 2f)
                        )
                    }
                }

                // Draw Rover
                rover?.let {
                    val centerX = animatedX * cellSize + cellSize / 2
                    val centerY = (gridSize - 1 - animatedY) * cellSize + cellSize / 2

                    rotate(animatedRotation, pivot = Offset(centerX, centerY)) {
                        drawCircle(
                            color = Color.Red,
                            radius = cellSize / 3,
                            center = Offset(centerX, centerY)
                        )
                        val arrowLength = cellSize / 3
                        val arrowOffset = Offset(centerX, centerY - arrowLength)

                        drawLine(
                            color = Color.White,
                            start = Offset(centerX, centerY),
                            end = arrowOffset,
                            strokeWidth = 6f
                        )
                    }
                }
            }
        }
    }
}