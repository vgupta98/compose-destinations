package io.github.vgupta98.compose_directions

sealed interface DestinationType1 {

    data object SomeType: DestinationType1

    data class SomeOtherType(
        val value: String
    ): DestinationType1
}

sealed interface DestinationType2 {

    data object Type1: DestinationType2

    data class Type2(
        val text: String
    ): DestinationType2
}

enum class DestinationType3 {
    TYPE1, TYPE2
}