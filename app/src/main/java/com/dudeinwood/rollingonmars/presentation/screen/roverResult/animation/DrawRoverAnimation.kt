package com.dudeinwood.rollingonmars.presentation.screen.roverResult.animation

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
    var direction = startDirection

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
                when (direction) {
                    'N' -> y += 1
                    'E' -> x += 1
                    'S' -> y -= 1
                    'W' -> x -= 1
                }
            }
        }
        updatePosition(x, y, rotation)
        delay(delayMillis)
    }
}