package io.github.vgupta98.compose_directions.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.vgupta98.compose_directions.presentation.hiltViewModel
import io.github.vgupta98.compose_directions.viewModel.ScreenDViewModel

@Composable
fun ScreenD() {
    val viewModel: ScreenDViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = viewModel.text)
    }
}