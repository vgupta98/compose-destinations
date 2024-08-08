package io.github.vgupta98.compose_directions.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.vgupta98.compose_directions.ScreenCConfig
import io.github.vgupta98.compose_directions.data.getConfig
import javax.inject.Inject

@HiltViewModel
class ScreenCViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val config: ScreenCConfig = savedStateHandle.getConfig()

    val text = config.text
}