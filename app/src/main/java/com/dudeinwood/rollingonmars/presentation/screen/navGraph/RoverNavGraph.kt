package com.dudeinwood.rollingonmars.presentation.screen.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dudeinwood.rollingonmars.presentation.screen.roverCommand.RoverCommandScreen
import com.dudeinwood.rollingonmars.presentation.screen.roverResult.RoverResultScreen
import com.dudeinwood.rollingonmars.presentation.screen.roverCommand.RoverViewModel

@Composable
fun RoverNavGraph() {
    val navController = rememberNavController()
    val viewModel: RoverViewModel = hiltViewModel()
    NavHost(navController, startDestination = Route.RoverCommandScreen.route) {
        composable(Route.RoverCommandScreen.route) { RoverCommandScreen(navController = navController, viewModel =  viewModel) }
        composable(Route.RoverResultScreen.route) { RoverResultScreen(viewModel) }
    }
}