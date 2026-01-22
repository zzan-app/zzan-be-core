package com.zzan.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(
        connectionFactory: RedisConnectionFactory,
        @Qualifier("redisObjectMapper")
        objectMapper: ObjectMapper
    ): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = connectionFactory

        // JSON Serializer 설정
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(objectMapper, Any::class.java)
        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        template.valueSerializer = jackson2JsonRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer

        template.setDefaultSerializer(jackson2JsonRedisSerializer)
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun stringRedisTemplate(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(connectionFactory)
    }
}
