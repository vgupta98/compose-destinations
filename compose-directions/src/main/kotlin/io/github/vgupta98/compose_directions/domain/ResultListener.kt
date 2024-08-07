package io.github.vgupta98.compose_directions.domain

import io.github.vgupta98.compose_directions.data.DestinationResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

internal interface ResultListener {

    suspend fun registerLambdaForResult(resultCode: Int, onResult: (DestinationResult<*>) -> Unit)

    suspend fun registerLambdaForResult(onResult: (Int, DestinationResult<*>) -> Unit)

    suspend fun updateResult(resultCode: Int, result: DestinationResult<*>)
}

internal class ResultListenerImpl : ResultListener {

    private val resultChannel = Channel<Pair<Int, DestinationResult<*>>>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun registerLambdaForResult(
        resultCode: Int,
        onResult: (DestinationResult<*>) -> Unit
    ) {
        resultChannel.receiveAsFlow().collect { result ->
            result.let {
                if (result.first == resultCode) {
                    onResult.invoke(result.second)
                }
            }
        }
    }

    override suspend fun registerLambdaForResult(onResult: (Int, DestinationResult<*>) -> Unit) {
        resultChannel.receiveAsFlow().collect { result ->
            onResult.invoke(result.first, result.second)
        }
    }

    override suspend fun updateResult(resultCode: Int, result: DestinationResult<*>) {
        resultChannel.send(resultCode to result)
    }
}