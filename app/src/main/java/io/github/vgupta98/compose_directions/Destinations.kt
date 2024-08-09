package io.github.vgupta98.compose_directions

import io.github.vgupta98.compose_directions.data.Destination
import io.github.vgupta98.compose_directions.data.DestinationConfig
import io.github.vgupta98.compose_directions.data.DestinationResult
import io.github.vgupta98.compose_directions.data.NoArgumentsDestination

object ScreenADestination: NoArgumentsDestination<ScreenADestination>()

object ScreenBDestination: NoArgumentsDestination<ScreenBDestination>()

data class ScreenBResult(
    val resultText: String
) : DestinationResult<ScreenBDestination>

class ScreenCDestination(
    override val config: ScreenCConfig
): Destination<ScreenCDestination>(config)

data class ScreenCConfig(
    val text: String,
): DestinationConfig<ScreenCDestination>

class ScreenDDestination(
    override val config: ScreenDConfig
): Destination<ScreenDDestination>(config)

data class ScreenDConfig(
    val destinationType1s: List<DestinationType1>,
    val destinationType2: DestinationType2,
    val destinationType3: DestinationType3
): DestinationConfig<ScreenDDestination>