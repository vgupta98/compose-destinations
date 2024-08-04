package io.github.vgupta98.compose_directions.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.vgupta98.compose_directions.ScreenBResult
import io.github.vgupta98.compose_directions.domain.Director
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenBViewModel @Inject constructor(
    private val director: Director
): ViewModel() {

    fun postResult() {
        viewModelScope.launch {

            director.postResult(
                result = ScreenBResult(resultText = "test result from screen B")
            )
        }
    }
}