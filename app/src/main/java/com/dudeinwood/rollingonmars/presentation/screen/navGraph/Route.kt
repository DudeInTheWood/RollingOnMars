package com.dudeinwood.rollingonmars.presentation.screen.navGraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String
) {
    object RoverCommandScreen : Route(route = "input_screen")
    object RoverResultScreen : Route(route = "rover_screen")
}