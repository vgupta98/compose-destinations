package io.github.vgupta98.compose_directions.data.adapter

import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

@Suppress("UNCHECKED_CAST")
@RestrictTo(RestrictTo.Scope.LIBRARY)
class SealedClassTypeAdapter<T : Any>(private val baseClass: Class<T>) : TypeAdapter<T>() {
    override fun write(out: JsonWriter, value: T) {
        out.beginObject()
        val typeName = value::class.simpleName
        out.name("type").value(typeName)
        out.name("data")
        val delegate: TypeAdapter<T> = Gson().getDelegateAdapter(null, TypeToken.get(value::class.java)) as TypeAdapter<T>
        delegate.write(out, value)

        out.endObject()
    }

    override fun read(reader: JsonReader): T {
        reader.beginObject()
        var typeName: String? = null
        var jsonObject: JsonObject? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "type" -> typeName = reader.nextString()
                "data" -> jsonObject = JsonParser.parseReader(reader).asJsonObject
            }
        }
        reader.endObject()

        val subclass = baseClass.kotlin.sealedSubclasses.find { it.simpleName == typeName }
            ?: throw JsonParseException("Unknown type: $typeName")

        val delegate = Gson().getDelegateAdapter(null, TypeToken.get(subclass.java))
        return delegate.fromJsonTree(jsonObject)
    }
}

@Suppress("UNCHECKED_CAST")
class SealedClassTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val klass = type.rawType.kotlin
        if (!klass.isSealed) return null

        return SealedClassTypeAdapter(type.rawType).nullSafe() as TypeAdapter<T>
    }
}