package com.dudeinwood.rollingonmars.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dudeinwood.rollingonmars.data.model.Rover

@Composable
fun RoverStatusView(
    rover: Rover,
    statusMessage: String,
    statusColor: Color = Color.Green,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Rover Position: (${rover.x}, ${rover.y})")
            Text(text = "Rover Direction: ${rover.direction}")
            Text(text = statusMessage, color = statusColor)
        }
    }
}