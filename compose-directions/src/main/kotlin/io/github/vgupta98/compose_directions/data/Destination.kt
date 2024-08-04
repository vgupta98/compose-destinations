package io.github.vgupta98.compose_directions.data

import androidx.annotation.RestrictTo

abstract class Destination<D : Destination<D>>(open val config: DestinationConfig<D>) {

    val destinationName: String = this::class.java.simpleName

    open val fullRoute = "$destinationName/{$CONFIG}?$RESULT_CODE={$RESULT_CODE}"

    companion object {
        const val CONFIG = "config"
        const val RESULT_CODE = "resultCode"
    }

    // overriding the invoke to get the actual string value of route to be passed while navigating
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    open operator fun invoke(resultCode: Int? = null): String {
        val route = destinationName.appendParams(
            CONFIG to this.config.stringifyConfig()
        )

        return if (resultCode == null) {
            route
        } else {
            "$route?$RESULT_CODE=$resultCode"
        }
    }
}

abstract class NoArgumentsDestination<D : Destination<D>> : Destination<D>(NoArgumentsConfig()) {

    override val fullRoute = "$destinationName?$RESULT_CODE={$RESULT_CODE}"

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    override operator fun invoke(resultCode: Int?): String = if (resultCode == null) {
        destinationName
    } else {
        "$destinationName?$RESULT_CODE=$resultCode"
    }
}

abstract class NavDialogDestination<D : Destination<D>>(
    config: DestinationConfig<D>
) : Destination<D>(config)

private fun String.appendParams(
    vararg params: Pair<String, Any?>,
): String {

    val builder = StringBuilder(this)

    params.forEach { pair ->
        pair.second?.run {
            builder.append("/$this")
        }
    }

    return builder.toString()
}