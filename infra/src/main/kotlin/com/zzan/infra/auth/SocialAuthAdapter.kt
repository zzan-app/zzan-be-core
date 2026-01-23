package com.zzan.infra.auth

import com.zzan.common.exception.CustomException
import com.zzan.common.type.SocialProvider
import com.zzan.infra.auth.kakao.KakaoAuthClient
import com.zzan.user.adapter.dto.`in`.SocialUserResult
import com.zzan.user.application.port.out.SocialAuthProvider
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class SocialAuthAdapter(
    private val kakaoAuthClient: KakaoAuthClient
) : SocialAuthProvider {

    override fun getLoginUrl(provider: SocialProvider): String {
        return when (provider) {
            SocialProvider.KAKAO -> kakaoAuthClient.getLoginUrl()
            else -> throw CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 로그인입니다.")
        }
    }

    override fun getAccessToken(provider: SocialProvider, code: String): String {
        return when (provider) {
            SocialProvider.KAKAO -> kakaoAuthClient.getAccessToken(code)
            else -> throw CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 로그인입니다.")
        }
    }

    override fun getUserInfo(provider: SocialProvider, accessToken: String): SocialUserResult {
        return when (provider) {
            SocialProvider.KAKAO -> {
                val kakaoUser = kakaoAuthClient.getUserInfo(accessToken)

                SocialUserResult(
                    socialId = kakaoUser.id.toString(),
                    name = kakaoUser.kakaoAccount.profile.nickname,
                    profileImageUrl = kakaoUser.kakaoAccount.profile.profileImageUrl,
                    provider = SocialProvider.KAKAO
                )
            }

            else -> throw CustomException(HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 로그인입니다.")
        }
    }
}
