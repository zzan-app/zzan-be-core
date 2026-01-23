package com.zzan.user.application.port.`in`

import com.zzan.common.type.SocialProvider
import com.zzan.user.adapter.dto.out.JwtResponse
import com.zzan.user.adapter.dto.out.LoginUrlResponse

interface AuthUseCase {
    fun getLoginUrl(provider: SocialProvider): LoginUrlResponse
    fun loginWithCode(provider: SocialProvider, code: String): JwtResponse
    fun loginWithToken(provider: SocialProvider, accessToken: String): JwtResponse
    fun refreshToken(refreshToken: String): JwtResponse
}
