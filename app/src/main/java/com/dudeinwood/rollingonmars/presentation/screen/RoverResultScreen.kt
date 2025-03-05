package com.dudeinwood.rollingonmars.presentation.screen

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dudeinwood.rollingonmars.utils.enum.Direction
import kotlinx.coroutines.delay

@Composable
fun RoverResultScreen(viewModel: RoverViewModel = hiltViewModel()) {
    val rover = viewModel.roverState.value

    val gridSize = 10 // Assuming 5x5 grid; modify as needed
    val cellSize = 100f // Each grid cell size in pixels

    val density = LocalDensity.current
    val gridSizeDp = with(density) { (gridSize * cellSize).toDp() } // Convert to Dp

    var animatedX by remember { mutableFloatStateOf(0f) }
    var animatedY by remember { mutableFloatStateOf(0f) }
    var animatedRotation by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(rover) {
        rover?.let {
            animateCommands(viewModel.savedCommand, 0, 0, Direction.N.value) { newX, newY, newRotation ->
                animatedX = newX
                animatedY = newY
                animatedRotation = newRotation
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        rover?.let {
            Text("Rover Position: (${it.x}, ${it.y})")
            Text("Rover Direction: ${it.direction}")
        } ?: Text("Error: Could not move rover", color = Color.Red)

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

suspend fun animateCommands(
    commands: String,
    startX: Int,
    startY: Int,
    startDirection: Char,
    updatePosition: (Float, Float, Float) -> Unit
) {
    var x = startX.toFloat()
    var y = startY.toFloat()
    var direction = startDirection // Keep track of the current direction

    var rotation = when (direction) {
        'N' -> 0f
        'E' -> 90f
        'S' -> 180f
        'W' -> 270f
        else -> 0f
    }

    val delayMillis = 300L // Delay per step

    for (command in commands) {
        when (command) {
            'L' -> {
                // Rotate counterclockwise and update direction
                direction = when (direction) {
                    'N' -> 'W'
                    'W' -> 'S'
                    'S' -> 'E'
                    'E' -> 'N'
                    else -> direction
                }
                rotation -= 90f
                if (rotation < 0) rotation += 360f
            }

            'R' -> {
                // Rotate clockwise and update direction
                direction = when (direction) {
                    'N' -> 'E'
                    'E' -> 'S'
                    'S' -> 'W'
                    'W' -> 'N'
                    else -> direction
                }
                rotation += 90f
                if (rotation >= 360) rotation -= 360f
            }

            'M' -> {
                // Move forward based on the updated direction
                when (direction) {
                    'N' -> y += 1
                    'E' -> x += 1
                    'S' -> y -= 1
                    'W' -> x -= 1
                }
            }
        }
        updatePosition(x, y, rotation)
        delay(delayMillis) // Wait before the next move
    }
}
