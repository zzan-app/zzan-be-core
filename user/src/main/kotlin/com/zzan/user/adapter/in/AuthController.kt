package com.zzan.user.adapter.`in`

import com.zzan.common.response.ApiResponse
import com.zzan.common.type.SocialProvider
import com.zzan.user.adapter.dto.`in`.SocialLoginRequest
import com.zzan.user.adapter.dto.out.JwtResponse
import com.zzan.user.adapter.dto.out.LoginUrlResponse
import com.zzan.user.application.port.`in`.AuthUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/auth")
class AuthController(
    private val authUseCase: AuthUseCase
) {
    @GetMapping("/{provider}/login-url")
    fun getLoginUrl(
        @PathVariable provider: String
    ): ApiResponse<LoginUrlResponse> {
        return ApiResponse.ok(
            authUseCase.getLoginUrl(SocialProvider(provider))
        )
    }

    @GetMapping("/{provider}/callback")
    fun handleCallback(
        @PathVariable provider: String,
        @RequestParam code: String
    ): ApiResponse<JwtResponse> {
        return ApiResponse.ok(
            authUseCase.loginWithCode(SocialProvider(provider), code)
        )
    }

    @PostMapping("/{provider}/login")
    fun loginWithToken(
        @PathVariable provider: String,
        @Valid @RequestBody request: SocialLoginRequest
    ): ApiResponse<JwtResponse> {
        return ApiResponse.ok(
            authUseCase.loginWithToken(
                SocialProvider(provider),
                request.accessToken
            )
        )
    }

    @PostMapping("/token/refresh")
    fun refreshToken(@RequestBody refreshToken: String): ApiResponse<JwtResponse> =
        ApiResponse.ok(authUseCase.refreshToken(refreshToken))

    @DeleteMapping("/token/refresh")
    fun deleteRefreshToken(): ApiResponse<Unit> =
        ApiResponse.ok(Unit)
}
