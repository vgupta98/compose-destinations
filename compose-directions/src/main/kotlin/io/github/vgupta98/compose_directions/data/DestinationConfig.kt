package io.github.vgupta98.compose_directions.data

import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.vgupta98.compose_directions.data.DestinationConfig.Companion.retrieveConfig
import io.github.vgupta98.compose_directions.data.adapter.EnumTypeAdapterFactory
import io.github.vgupta98.compose_directions.data.adapter.SealedClassTypeAdapterFactory
import java.lang.ClassCastException

interface DestinationConfig<D : Destination<D>> {

    companion object {

        @RestrictTo(RestrictTo.Scope.LIBRARY)
        inline fun <reified T> String.retrieveConfig(): T {
            val config = gson.fromJson(this, T::class.java)
            return config
        }

        @RestrictTo(RestrictTo.Scope.LIBRARY)
        val gson: Gson = GsonBuilder()
            .registerTypeAdapterFactory(SealedClassTypeAdapterFactory())
            .registerTypeAdapterFactory(EnumTypeAdapterFactory())
            .create()
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun stringifyConfig(): String {
        val json = gson.toJson(this)
        return json
    }
}

internal class NoArgumentsConfig<D: Destination<D>>: DestinationConfig<D>

@MainThread
inline fun <reified T : DestinationConfig<D>, D : Destination<D>> SavedStateHandle.getConfig(): T {
    return try {
        requireNotNull(this.get<String>(Destination.CONFIG)?.retrieveConfig<T>()) {
            "getConfig: Check if you correctly passed config for this destination."
        }
    } catch (e: ClassCastException) {
        remove<DestinationConfig<D>>(Destination.CONFIG)
        throw e
    }
}