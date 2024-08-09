package io.github.vgupta98.compose_directions.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.vgupta98.compose_directions.DestinationType1
import io.github.vgupta98.compose_directions.ScreenDConfig
import io.github.vgupta98.compose_directions.data.getConfig
import javax.inject.Inject

@HiltViewModel
class ScreenDViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val config: ScreenDConfig = savedStateHandle.getConfig()

    val text = getText(config.destinationType1s.first())

    val destinationType2 = config.destinationType2
}

private fun getText(destinationType1: DestinationType1): String {
    return when(destinationType1) {
        is DestinationType1.SomeOtherType -> destinationType1.value
        DestinationType1.SomeType -> "some type"
    }
}