package com.dudeinwood.rollingonmars.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun RoverCommandScreen(navController: NavController, viewModel: RoverViewModel = hiltViewModel()) {
    var gridSize by remember { mutableStateOf("") }
    var obstacles by remember { mutableStateOf("") }
    var commands by remember { mutableStateOf("") }
    val errorMessage by viewModel.errorMessage

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = gridSize, onValueChange = { gridSize = it }, label = { Text("Grid Size (e.g., 5,5)") })
        TextField(value = obstacles, onValueChange = { obstacles = it }, label = { Text("Obstacles (e.g., [1,2];[3,4])") })
        TextField(value = commands, onValueChange = { commands = it }, label = { Text("Commands (e.g., LMMRMM)") })

        if (errorMessage != null) {
            Text(errorMessage!!, color = Color.Red)
        }

        Button(onClick = {
            viewModel.moveRover(gridSize, obstacles, commands)
            if (errorMessage == null) {
                navController.navigate("rover_screen")
            }
        }) {
            Text("Move Rover")
        }
    }
}