package com.zzan.user.application.service

import com.zzan.common.exception.CustomException
import com.zzan.common.type.SocialProvider
import com.zzan.common.util.JwtUtil
import com.zzan.user.adapter.dto.out.JwtResponse
import com.zzan.user.adapter.dto.out.LoginUrlResponse
import com.zzan.user.application.port.`in`.AuthUseCase
import com.zzan.user.application.port.`in`.UserUseCase
import com.zzan.user.application.port.out.SocialAuthProvider
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val socialAuthProvider: SocialAuthProvider,
    private val userUseCase: UserUseCase,
    private val jwtUtil: JwtUtil
) : AuthUseCase {

    override fun getLoginUrl(provider: SocialProvider): LoginUrlResponse {
        val url = socialAuthProvider.getLoginUrl(provider)
        return LoginUrlResponse(url)
    }

    override fun loginWithCode(provider: SocialProvider, code: String): JwtResponse {
        val accessToken = socialAuthProvider.getAccessToken(provider, code)
        return loginWithToken(provider, accessToken)
    }

    override fun loginWithToken(provider: SocialProvider, accessToken: String): JwtResponse {
        val socialUser = socialAuthProvider.getUserInfo(provider, accessToken)

        val user = userUseCase.findBySocialId(socialUser.socialId, provider)
            ?: userUseCase.createFromSocial(socialUser)

        val userId = user.id
            ?: throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "User ID 값이 존재하지 않습니다.")

        return JwtResponse(
            accessToken = jwtUtil.createAccessToken(user.toTokenUserInfo()),
            refreshToken = jwtUtil.createRefreshToken(userId)
        )
    }

    override fun refreshToken(refreshToken: String): JwtResponse {
        if (!jwtUtil.isValidToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.")
        }

        val userId = jwtUtil.getUserIdFromToken(refreshToken)
            ?: throw CustomException(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.")

        val user = userUseCase.getById(userId)

        return JwtResponse(
            accessToken = jwtUtil.createAccessToken(user.toTokenUserInfo()),
            refreshToken = refreshToken
        )
    }
}
