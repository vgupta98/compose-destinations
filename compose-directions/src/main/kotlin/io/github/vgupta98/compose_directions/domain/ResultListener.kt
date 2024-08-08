package io.github.vgupta98.compose_directions.domain

import io.github.vgupta98.compose_directions.data.DestinationResult
import kotlinx.coroutines.flow.MutableSharedFlow

internal interface ResultListener {

    suspend fun registerLambdaForResult(resultCode: Int, onResult: (DestinationResult<*>) -> Unit)

    suspend fun registerLambdaForResult(onResult: (Int, DestinationResult<*>) -> Unit)

    suspend fun updateResult(resultCode: Int, result: DestinationResult<*>)
}

internal class ResultListenerImpl : ResultListener {

    private val resultChannel = MutableSharedFlow<Pair<Int, DestinationResult<*>>>()

    override suspend fun registerLambdaForResult(
        resultCode: Int,
        onResult: (DestinationResult<*>) -> Unit
    ) {
        resultChannel.collect { result ->
            result.let {
                if (result.first == resultCode) {
                    onResult.invoke(result.second)
                }
            }
        }
    }

    override suspend fun registerLambdaForResult(onResult: (Int, DestinationResult<*>) -> Unit) {
        resultChannel.collect { result ->
            onResult.invoke(result.first, result.second)
        }
    }

    override suspend fun updateResult(resultCode: Int, result: DestinationResult<*>) {
        resultChannel.emit(resultCode to result)
    }
}