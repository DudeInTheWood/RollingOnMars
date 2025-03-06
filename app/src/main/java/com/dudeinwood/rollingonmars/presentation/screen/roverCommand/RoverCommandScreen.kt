package com.dudeinwood.rollingonmars.presentation.screen.roverCommand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dudeinwood.rollingonmars.R
import com.dudeinwood.rollingonmars.presentation.ui.component.RoverStatusView
import com.dudeinwood.rollingonmars.presentation.ui.theme.Pink80
import com.dudeinwood.rollingonmars.presentation.ui.theme.errorColor

@Composable
fun RoverCommandScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RoverViewModel = hiltViewModel()
) {
    val errorMessage by viewModel.errorMessage

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.gridSize,
                onValueChange = { viewModel.updateGridSize(it) },
                label = { Text(stringResource(R.string.hint_grid)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions.Default,
            )
            OutlinedTextField(
                value = viewModel.obstacles,
                onValueChange = { viewModel.updateObstacles(it) },
                label = { Text(stringResource(R.string.hint_obstacle)) }
            )
            OutlinedTextField(
                value = viewModel.commands,
                onValueChange = { viewModel.updateCommands(it) },
                label = { Text(stringResource(R.string.hint_command)) }
            )
            Button(onClick = {
                viewModel.moveRover()
                if (errorMessage == null) {
                    navController.navigate("rover_screen")
                }
            }) {
                Text("Move Rover")
            }

            if (errorMessage != null) {
                viewModel.roverState.value?.let {
                    RoverStatusView(
                        rover = it,
                        statusMessage = errorMessage!!,
                        statusColor = errorColor
                    )
                } ?: Text(errorMessage!!, color = errorColor)
            }
        }
    }
}