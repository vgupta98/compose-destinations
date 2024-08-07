package io.github.vgupta98.compose_directions.domain

import io.github.vgupta98.compose_directions.data.Destination
import io.github.vgupta98.compose_directions.data.DestinationResult
import io.github.vgupta98.compose_directions.data.NavigationIntent
import io.github.vgupta98.compose_directions.domain.provider.DomainProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class DirectorImpl internal constructor(
    internal val resultListener: ResultListener
): Director {

    private val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    internal val navigationFlow: Flow<NavigationIntent>
        get() = navigationChannel.receiveAsFlow()

    override suspend fun directTo(
        destination: Destination<*>,
        popUpToDestination: Destination<*>?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = destination(),
                popUpToRoute = popUpToDestination?.fullRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop
            )
        )
    }

    override suspend fun directBack(destination: Destination<*>?, inclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = destination?.fullRoute,
                inclusive = inclusive
            )
        )
    }

    override suspend fun directForResult(
        destination: Destination<*>,
        isSingleTop: Boolean,
        resultCode: Int
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = destination(resultCode),
                popUpToRoute = null,
                inclusive = false,
                isSingleTop = isSingleTop,
            )
        )
    }

    override suspend fun onDirectionResult(
        resultCode: Int,
        onResult: (DestinationResult<*>) -> Unit
    ) {
        resultListener.registerLambdaForResult(resultCode, onResult)
    }

    override suspend fun onDirectionResult(onResult: (Int, DestinationResult<*>) -> Unit) {
        resultListener.registerLambdaForResult(onResult)
    }

    override suspend fun postResult(result: DestinationResult<*>) {
        navigationChannel.send(
            NavigationIntent.NavigateBackWithResult(
                result = result
            )
        )
    }

    override suspend fun quitApp() {
        navigationChannel.send(
            NavigationIntent.Quit
        )
    }
}

object DirectorProvider {

    private val resultListener = DomainProvider.provideResultListener()

    fun provide() = DomainProvider.provideDirector(resultListener)
}