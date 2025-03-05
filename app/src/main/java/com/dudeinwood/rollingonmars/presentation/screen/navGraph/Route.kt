package com.dudeinwood.rollingonmars.presentation.screen.navGraph

sealed class Route(
    val route: String
) {
    data object RoverCommandScreen : Route(route = "input_screen")
    data object RoverResultScreen : Route(route = "rover_screen")
}