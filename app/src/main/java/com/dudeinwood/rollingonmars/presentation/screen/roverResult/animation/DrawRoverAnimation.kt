package com.dudeinwood.rollingonmars.presentation.screen.roverResult.animation

import com.dudeinwood.rollingonmars.utils.enums.Command
import com.dudeinwood.rollingonmars.utils.enums.Direction
import com.dudeinwood.rollingonmars.utils.enums.Direction.Companion.directionFromChar
import kotlinx.coroutines.delay
suspend fun animateCommands(
    commands: String,
    startX: Int,
    startY: Int,
    startDirection: Char,
    updatePosition: (Float, Float, Float) -> Unit
) {
    var x = startX.toFloat()
    var y = startY.toFloat()
    var direction = directionFromChar(startDirection)

    var rotation = when (direction) {
        Direction.N -> 0f
        Direction.E -> 90f
        Direction.S -> 180f
        Direction.W -> 270f
    }

    val delayMillis = 300L // Delay per step

    for (command in commands) {
        when (command) {
            Command.L.value -> {
                direction = when (direction) {
                    Direction.N -> Direction.W
                    Direction.W -> Direction.S
                    Direction.S -> Direction.E
                    Direction.E -> Direction.N
                }
                rotation -= 90f
                if (rotation < 0) rotation += 360f
            }

            Command.R.value -> {
                direction = when (direction) {
                    Direction.N -> Direction.E
                    Direction.E -> Direction.S
                    Direction.S -> Direction.W
                    Direction.W -> Direction.N
                }
                rotation += 90f
                if (rotation >= 360) rotation -= 360f
            }

            Command.M.value -> {
                when (direction) {
                    Direction.N -> y += 1
                    Direction.E -> x += 1
                    Direction.S -> y -= 1
                    Direction.W -> x -= 1
                }
            }
        }
        updatePosition(x, y, rotation)
        delay(delayMillis)
    }
}