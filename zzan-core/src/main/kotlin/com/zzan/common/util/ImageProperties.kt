package com.zzan.common.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.image")
data class ImageProperties(
    val baseUrl: String
)
