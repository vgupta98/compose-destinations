package io.github.vgupta98.compose_directions.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import io.github.vgupta98.compose_directions.data.Destination
import io.github.vgupta98.compose_directions.data.NavigationIntent
import io.github.vgupta98.compose_directions.domain.Director
import io.github.vgupta98.compose_directions.domain.DirectorImpl

@Composable
internal fun NavigationCallback(
    activity: ComponentActivity,
    director: Director,
    navHostController: NavHostController,
) {

    val resultListener = remember { (director as DirectorImpl).resultListener }
    val navigationFlow = remember { (director as DirectorImpl).navigationFlow }

    LaunchedEffect(Unit) {

        navigationFlow.collect { navigationIntent ->
            if (activity.isFinishing) {
                return@collect
            }

            Log.d("Compose Directions NavigationCallback", "NavigationCallback: $navigationIntent")

            when (navigationIntent) {
                is NavigationIntent.NavigateBack -> {
                    val route = navigationIntent.route
                    if (route != null) {
                        navHostController.popBackStack(route, navigationIntent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> navHostController.navigate(navigationIntent.route) {
                    launchSingleTop = navigationIntent.isSingleTop
                    navigationIntent.popUpToRoute?.let { popUpToRoute ->
                        popUpTo(popUpToRoute) { inclusive = navigationIntent.inclusive }
                    }
                }

                is NavigationIntent.Quit -> {
                    activity.finishAffinity()
                }

                is NavigationIntent.NavigateBackWithResult -> {
                    val resultCode =
                        navHostController.currentBackStackEntry?.arguments?.getInt(Destination.RESULT_CODE)

                    resultCode?.let {
                        resultListener.updateResult(resultCode, navigationIntent.result)
                        navHostController.popBackStack()
                    }
                }
            }
        }
    }
}
