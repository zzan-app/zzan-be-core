package com.zzan.infra.auth.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao")
class KakaoAuthProperties {
    lateinit var clientId: String
    lateinit var redirectUri: String
}
