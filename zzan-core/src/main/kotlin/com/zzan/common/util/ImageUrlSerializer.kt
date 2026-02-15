package com.zzan.common.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(ImageProperties::class)
class ImageUrlSerializer(
    private val imageProperties: ImageProperties
) : JsonSerializer<String>() {

    override fun serialize(value: String?, gen: JsonGenerator, serializers: SerializerProvider) {
        if (value.isNullOrBlank()) {
            gen.writeNull()
            return
        }

        val fullUrl = if (value.startsWith("http")) value
        else "${imageProperties.baseUrl}$value"

        gen.writeString(fullUrl)
    }

    override fun serializeWithType(
        value: String?,
        gen: JsonGenerator,
        serializers: SerializerProvider,
        typeSer: TypeSerializer
    ) {
        serialize(value, gen, serializers)
    }
}
