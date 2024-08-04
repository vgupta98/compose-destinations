package io.github.vgupta98.compose_directions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.vgupta98.compose_directions.domain.Director
import io.github.vgupta98.compose_directions.presentation.NavHost
import io.github.vgupta98.compose_directions.presentation.composable
import io.github.vgupta98.compose_directions.ui.theme.ComposedirectionsTheme
import io.github.vgupta98.compose_directions.screen.ScreenAComposable
import io.github.vgupta98.compose_directions.screen.ScreenBComposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var director: Director

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposedirectionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        startDestination = ScreenA,
                        modifier = Modifier.padding(innerPadding),
                        director = director,
                        activity = this
                    ) {
                        composable<ScreenA> {
                            ScreenAComposable()
                        }
                        composable<ScreenB> {
                            ScreenBComposable()
                        }
                    }
                }
            }
        }
    }
}