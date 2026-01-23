package com.zzan.infra.place.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.map")
class KakaoMapProperties {
    lateinit var restKey: String
}
