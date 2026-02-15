package com.zzan.common.type

import com.zzan.common.exception.CustomException
import org.springframework.http.HttpStatus

enum class SocialProvider {
    KAKAO,
    GOOGLE,
    APPLE;

    companion object {
        operator fun invoke(provider: String): SocialProvider {
            return entries.find { it.name.equals(provider, ignoreCase = true) }
                ?: throw CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 제공자입니다: $provider")
        }
    }
}
