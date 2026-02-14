package com.zzan.liquor.adapter.`in`

import com.zzan.common.annotation.CurrentUser
import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.response.ApiResponse
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.application.port.`in`.LiquorReviewUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/liquors/{liquorId}/reviews")
class LiquorReviewController(
    private val liquorReviewUseCase: LiquorReviewUseCase
) {
    @GetMapping("/me")
    fun getMyReview(
        @CurrentUser userId: String,
        @PathVariable liquorId: String
    ): ApiResponse<LiquorReviewResponse?> =
        ApiResponse.ok(liquorReviewUseCase.find(userId, liquorId))

    @GetMapping
    fun getList(
        @PathVariable liquorId: String,
        @Valid pageRequest: CursorPageRequest,
    ): ApiResponse<CursorPageResponse<LiquorReviewResponse>> =
        ApiResponse.ok(liquorReviewUseCase.getList(liquorId, pageRequest))

    @PostMapping
    fun create(
        @CurrentUser userId: String,
        @PathVariable liquorId: String,
        @Valid @RequestBody request: CreateLiquorReview
    ): ApiResponse<Unit> {
        liquorReviewUseCase.create(userId, liquorId, request)
        return ApiResponse.ok(Unit)
    }

    @PutMapping()
    fun update(
        @CurrentUser userId: String,
        @PathVariable liquorId: String,
        @Valid @RequestBody request: UpdateLiquorReview
    ): ApiResponse<Unit> {
        liquorReviewUseCase.update(userId, liquorId, request)
        return ApiResponse.ok(Unit)
    }

    @DeleteMapping
    fun delete(
        @CurrentUser userId: String,
        @PathVariable liquorId: String
    ): ApiResponse<Unit> {
        liquorReviewUseCase.delete(userId, liquorId)
        return ApiResponse.ok(Unit)
    }
}
