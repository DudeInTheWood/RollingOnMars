package com.dudeinwood.rollingonmars.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dudeinwood.rollingonmars.presentation.screen.roverCommand.RoverViewModel
import com.dudeinwood.rollingonmars.presentation.screen.navGraph.RoverNavGraph
import com.dudeinwood.rollingonmars.presentation.ui.theme.RollingOnMarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RollingOnMarsTheme {
                val navController = rememberNavController()
                val viewModel: RoverViewModel = hiltViewModel()
                RoverNavGraph(navController, viewModel)
            }
        }
    }
}