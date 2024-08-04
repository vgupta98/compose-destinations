package io.github.vgupta98.compose_directions.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

interface DestinationNavGraphBuilder {

    fun addComposable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        enterTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
        exitTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
        popEnterTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
            enterTransition,
        popExitTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
            exitTransition,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    )

    fun addDialog(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        dialogProperties: DialogProperties = DialogProperties(),
        content: @Composable (NavBackStackEntry) -> Unit
    )
}

internal class DestinationNavGraphBuilderImpl(private val navGraphBuilder: NavGraphBuilder): DestinationNavGraphBuilder {
    override fun addComposable(
        route: String,
        arguments: List<NamedNavArgument>,
        deepLinks: List<NavDeepLink>,
        enterTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?,
        exitTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?,
        popEnterTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?,
        popExitTransition: (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?,
        content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content
        )
    }

    override fun addDialog(
        route: String,
        arguments: List<NamedNavArgument>,
        deepLinks: List<NavDeepLink>,
        dialogProperties: DialogProperties,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        navGraphBuilder.dialog(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            dialogProperties = dialogProperties,
            content = content
        )
    }
}