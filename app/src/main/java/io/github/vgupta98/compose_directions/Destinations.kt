package io.github.vgupta98.compose_directions

import io.github.vgupta98.compose_directions.data.DestinationResult
import io.github.vgupta98.compose_directions.data.NoArgumentsDestination

object ScreenA: NoArgumentsDestination<ScreenA>()

object ScreenB: NoArgumentsDestination<ScreenB>()

data class ScreenBResult(
    val resultText: String
) : DestinationResult<ScreenB>