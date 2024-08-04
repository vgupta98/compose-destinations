package io.github.vgupta98.compose_directions.data

internal sealed interface NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent

    data class NavigateBackWithResult(
        val result: DestinationResult<*>
    ) : NavigationIntent

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent

    data object Quit : NavigationIntent
}