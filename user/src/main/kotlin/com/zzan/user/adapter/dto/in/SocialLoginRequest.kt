package com.zzan.user.adapter.dto.`in`

import jakarta.validation.constraints.NotEmpty

data class SocialLoginRequest(
    @field:NotEmpty(message = "액세스 토큰은 비어 있을 수 없습니다.")
    val accessToken: String
)
