package com.dudeinwood.rollingonmars.presentation.screen.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dudeinwood.rollingonmars.presentation.screen.RoverCommandScreen
import com.dudeinwood.rollingonmars.presentation.screen.RoverResultScreen
import com.dudeinwood.rollingonmars.presentation.screen.RoverViewModel

@Composable
fun RoverNavGraph(navController: NavHostController, viewModel: RoverViewModel) {
    NavHost(navController, startDestination = Route.RoverCommandScreen.route) {
        composable(Route.RoverCommandScreen.route) { RoverCommandScreen(navController, viewModel) }
        composable(Route.RoverResultScreen.route) { RoverResultScreen(viewModel) }
    }
}