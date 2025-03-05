package com.dudeinwood.rollingonmars.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dudeinwood.rollingonmars.presentation.screen.RoverCommandScreen
import com.dudeinwood.rollingonmars.presentation.screen.RoverResultScreen
import com.dudeinwood.rollingonmars.presentation.screen.RoverViewModel
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