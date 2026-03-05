package com.zzan.liquor.adapter.`in`

import com.zzan.core.annotation.CurrentUser
import com.zzan.core.dto.CursorPageRequest
import com.zzan.core.dto.CursorPageResponse
import com.zzan.core.dto.ExistResponse
import com.zzan.core.response.ApiResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse
import com.zzan.liquor.application.port.`in`.LiquorScrapUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/liquors/scraps")
class LiquorScrapController(
    private val liquorScrapUseCase: LiquorScrapUseCase
) {
    @GetMapping
    fun getList(
        @CurrentUser userId: String,
        @Valid pageRequest: CursorPageRequest
    ): ApiResponse<CursorPageResponse<LiquorInfoResponse>> =
        ApiResponse.ok(liquorScrapUseCase.getList(userId, pageRequest))

    @GetMapping("/{liquorId}")
    fun getExist(
        @CurrentUser userId: String,
        @PathVariable liquorId: String
    ): ApiResponse<ExistResponse> =
        ApiResponse.ok(liquorScrapUseCase.getExist(userId, liquorId))

    @PostMapping("/{liquorId}")
    fun create(
        @CurrentUser userId: String,
        @PathVariable liquorId: String
    ): ApiResponse<Unit> {
        liquorScrapUseCase.create(userId, liquorId)
        return ApiResponse.ok(Unit)
    }

    @DeleteMapping("/{liquorId}")
    fun delete(
        @CurrentUser userId: String,
        @PathVariable liquorId: String
    ): ApiResponse<Unit> {
        liquorScrapUseCase.delete(userId, liquorId)
        return ApiResponse.ok(Unit)
    }
}
