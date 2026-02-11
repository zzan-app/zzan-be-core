package com.zzan.user.adapter.`in`

import com.zzan.common.annotation.CurrentUser
import com.zzan.common.response.ApiResponse
import com.zzan.user.adapter.dto.`in`.UpdateUser
import com.zzan.user.adapter.dto.out.UserDetailResponse
import com.zzan.user.application.port.`in`.UserUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userUseCase: UserUseCase,
) {
    @GetMapping("/me")
    fun getCurrentUserDetail(@CurrentUser userId: String): ApiResponse<UserDetailResponse> =
        ApiResponse.ok(userUseCase.getUserDetail(userId))

    @PutMapping("/me")
    fun update(
        @CurrentUser userId: String,
        @Valid @RequestBody request: UpdateUser
    ): ApiResponse<Unit> {
        return ApiResponse.ok(userUseCase.update(userId, request))
    }
}
