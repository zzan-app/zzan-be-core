package com.zzan.user.application.port.out

import com.zzan.common.type.SocialProvider
import com.zzan.user.adapter.dto.`in`.SocialUserResult

interface SocialAuthProvider {
    fun getLoginUrl(provider: SocialProvider): String
    fun getAccessToken(provider: SocialProvider, code: String): String
    fun getUserInfo(provider: SocialProvider, accessToken: String): SocialUserResult
}
