package io.github.vgupta98.compose_directions.domain.provider

import io.github.vgupta98.compose_directions.domain.Director
import io.github.vgupta98.compose_directions.domain.DirectorImpl
import io.github.vgupta98.compose_directions.domain.ResultListener
import io.github.vgupta98.compose_directions.domain.ResultListenerImpl

internal object DomainProvider {

    fun provideResultListener(): ResultListener =
        ResultListenerImpl()

    fun provideDirector(resultListener: ResultListener): Director = DirectorImpl(resultListener)
}