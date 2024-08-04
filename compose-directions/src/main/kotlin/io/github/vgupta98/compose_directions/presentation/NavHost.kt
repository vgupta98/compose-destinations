package io.github.vgupta98.compose_directions.presentation

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.vgupta98.compose_directions.data.Destination
import io.github.vgupta98.compose_directions.data.Destination.Companion.RESULT_CODE
import io.github.vgupta98.compose_directions.data.Destination.Companion.CONFIG
import io.github.vgupta98.compose_directions.data.NavDialogDestination
import io.github.vgupta98.compose_directions.data.NoArgumentsDestination
import io.github.vgupta98.compose_directions.domain.Director
import kotlin.reflect.full.isSubclassOf

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    startDestination: Destination<*>,
    route: String? = null,
    director: Director,
    activity: ComponentActivity,
    builder: DestinationNavGraphBuilder.() -> Unit = {},
) {
    val navController = rememberNavController()

    NavigationCallback(activity = activity, director = director, navHostController = navController)
    NavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
    ) {
        val wrapper = DestinationNavGraphBuilderImpl(this)
        builder(wrapper)
    }
}

inline fun <reified D : Destination<D>> DestinationNavGraphBuilder.composable(
    noinline enterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    noinline exitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    noinline popEnterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    noinline popExitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {

    val route = when {
        D::class.isSubclassOf(NoArgumentsDestination::class) -> "${D::class.simpleName.toString()}?$RESULT_CODE={$RESULT_CODE}"
        else -> "${D::class.simpleName.toString()}/{$CONFIG}?$RESULT_CODE={$RESULT_CODE}"
    }
    val arguments = when {
        D::class.isSubclassOf(NoArgumentsDestination::class) -> listOf(
            navArgument(RESULT_CODE) { type = NavType.IntType }
        )
        else -> listOf(
            navArgument(CONFIG) { type = NavType.StringType },
            navArgument(RESULT_CODE) { type = NavType.IntType }
        )
    }

    addComposable(
        route = route,
        arguments = arguments,
        deepLinks = emptyList(),
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

inline fun <reified D : NavDialogDestination<D>> DestinationNavGraphBuilder.dialog(
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable (NavBackStackEntry) -> Unit
) {
    val route = "${D::class.simpleName.toString()}/{$CONFIG}?$RESULT_CODE={$RESULT_CODE}"

    addDialog(
        route = route,
        arguments = listOf(
            navArgument(CONFIG) { type = NavType.StringType },
            navArgument(RESULT_CODE) { type = NavType.IntType }
        ),
        deepLinks = emptyList(),
        dialogProperties = dialogProperties,
        content = content
    )
}