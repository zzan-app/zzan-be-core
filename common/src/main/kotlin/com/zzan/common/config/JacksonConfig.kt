package com.zzan.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.SpringHandlerInstantiator

@Configuration
class JacksonConfig(
    private val beanFactory: AutowireCapableBeanFactory
) {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerKotlinModule() // Kotlin 지원 모듈 등록
            registerModule(JavaTimeModule()) // Java 8 날짜 및 시간 API 지원 모듈 등록
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // ISO-8601 형식으로 날짜 직렬화
            setHandlerInstantiator(SpringHandlerInstantiator(beanFactory)) // Spring Bean 주입 지원
        }
    }
}
