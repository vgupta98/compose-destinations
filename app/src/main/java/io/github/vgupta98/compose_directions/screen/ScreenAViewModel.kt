package io.github.vgupta98.compose_directions.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.vgupta98.compose_directions.ScreenB
import io.github.vgupta98.compose_directions.ScreenBResult
import io.github.vgupta98.compose_directions.domain.Director
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenAViewModel @Inject constructor(
    private val director: Director
): ViewModel() {

    var resultText by mutableStateOf("")
        private set

    init {
        // listen for result in init
        viewModelScope.launch {
            director.onDirectionResult(RESULT_CODE) { result ->
                val destinationResult = result as ScreenBResult
                resultText = destinationResult.resultText
            }
        }
    }

    fun navigateToBForResult(
    ) {
        viewModelScope.launch {
            director.directForResult(
                destination = ScreenB,
                resultCode = RESULT_CODE
            )
        }
    }

    companion object {

        internal const val RESULT_CODE = 100
    }
}