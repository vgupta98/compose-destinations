package io.github.vgupta98.compose_directions.data.adapter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class EnumTypeAdapter<T : Enum<T>>(private val enumClass: Class<T>) : TypeAdapter<T>() {
    override fun write(out: JsonWriter, value: T) {
        out.value(value.name)
    }

    override fun read(reader: JsonReader): T {
        val enumName = reader.nextString()
        return java.lang.Enum.valueOf(enumClass, enumName)
    }
}

@Suppress("UNCHECKED_CAST")
class EnumTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType
        return if (rawType.isEnum) {
            EnumTypeAdapter(rawType as Class<out Enum<*>>) as TypeAdapter<T>
        } else {
            null
        }
    }
}