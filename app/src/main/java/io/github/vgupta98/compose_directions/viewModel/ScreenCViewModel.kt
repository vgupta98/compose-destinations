package io.github.vgupta98.compose_directions.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.vgupta98.compose_directions.DestinationType1
import io.github.vgupta98.compose_directions.DestinationType2
import io.github.vgupta98.compose_directions.ScreenCConfig
import io.github.vgupta98.compose_directions.ScreenDConfig
import io.github.vgupta98.compose_directions.ScreenDDestination
import io.github.vgupta98.compose_directions.data.getConfig
import io.github.vgupta98.compose_directions.domain.Director
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenCViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val director: Director,
) : ViewModel() {

    private val config: ScreenCConfig = savedStateHandle.getConfig()

    val text = config.text

    fun navigateToD() {
        viewModelScope.launch {
            director.directTo(
                destination = ScreenDDestination(
                    config = ScreenDConfig(
                        destinationType1s = listOf(
                            DestinationType1.SomeType,
                            DestinationType1.SomeOtherType("random text")
                        ),
                        destinationType2 = DestinationType2.Type1
                    )
                )
            )
        }
    }
}