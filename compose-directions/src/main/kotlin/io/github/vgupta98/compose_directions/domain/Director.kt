package io.github.vgupta98.compose_directions.domain

import io.github.vgupta98.compose_directions.data.Destination
import io.github.vgupta98.compose_directions.data.DestinationResult

interface Director {

    suspend fun directTo(
        destination: Destination<*>,
        popUpToDestination: Destination<*>? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    suspend fun directBack(
        destination: Destination<*>? = null,
        inclusive: Boolean = false,
    )

    suspend fun directForResult(
        destination: Destination<*>,
        isSingleTop: Boolean = false,
        resultCode: Int,
    )

    suspend fun onDirectionResult(
        resultCode: Int,
        onResult: (DestinationResult<*>) -> Unit
    )

    suspend fun onDirectionResult(
        onResult: (Int, DestinationResult<*>) -> Unit
    )

    suspend fun postResult(
        result: DestinationResult<*>
    )

    suspend fun quitApp()
}