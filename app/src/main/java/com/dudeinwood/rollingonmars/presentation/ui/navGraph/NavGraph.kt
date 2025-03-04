package com.dudeinwood.rollingonmars.presentation.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

//@Composable
//fun NavGraph(
//    startDestination: String
//) {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = startDestination) {
//        navigation(
//            route = Route.NewsNavigation.route,
//            startDestination = Route.NewsNavigatorScreen.route
//        ) {
//            composable(route = Route.NewsNavigatorScreen.route){
//                NewsNavigator()
//            }
//        }
//    }
//
//    NavHost(navController, startDestination = startDestination) {
//        composable("input_screen") { InputScreen(viewModel(), navController) }
//        composable("rover_screen") { RoverScreen(viewModel()) }
//    }
//}