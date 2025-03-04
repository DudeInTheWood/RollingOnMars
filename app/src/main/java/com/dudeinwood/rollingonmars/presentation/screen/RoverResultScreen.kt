package com.dudeinwood.rollingonmars.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RoverResultScreen(viewModel: RoverViewModel = hiltViewModel()) {
    val rover = viewModel.roverState.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        rover?.let {
            Text("Rover Position: (${it.x}, ${it.y})")
            Text("Rover Direction: ${it.direction}")
        } ?: Text("Error: Could not move rover", color = Color.Red)
    }
}