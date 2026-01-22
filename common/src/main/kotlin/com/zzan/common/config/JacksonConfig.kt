package com.zzan.common.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class JacksonConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerKotlinModule() // Kotlin 지원을 위한 모듈 등록
            registerModule(JavaTimeModule()) // Java 8 Time API 지원을 위한 모듈 등록
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 날짜를 ISO-8601 형식으로 직렬화
        }
    }

    @Bean("redisObjectMapper")
    fun redisObjectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            // Kotlin 지원을 위한 모듈 등록
            registerKotlinModule()

            // Java 8 Time API 지원을 위한 모듈 등록
            registerModule(JavaTimeModule())

            // 모든 필드에 대해 직렬화/역직렬화 허용
            setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)

            // 타입 정보 포함 (역직렬화 시 타입 안전성)
            activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.EVERYTHING,
                JsonTypeInfo.As.PROPERTY
            )
        }
    }
}
