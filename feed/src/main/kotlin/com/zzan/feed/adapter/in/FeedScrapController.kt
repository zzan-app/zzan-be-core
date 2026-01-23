package com.zzan.feed.adapter.`in`

import com.zzan.common.annotation.CurrentUser
import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.ExistResponse
import com.zzan.common.response.ApiResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.`in`.FeedScrapUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feeds/scraps")
class FeedScrapController(
    private val feedScrapUseCase: FeedScrapUseCase,
) {
    @GetMapping
    fun getMine(
        @CurrentUser userId: String,
        @Valid pageRequest: CursorPageRequest
    ): ApiResponse<CursorPageResponse<FeedInfoResponse>> =
        ApiResponse.ok(feedScrapUseCase.getList(userId, pageRequest))

    @GetMapping("/{feedId}")
    fun isExist(
        @CurrentUser userId: String,
        @PathVariable feedId: String
    ): ApiResponse<ExistResponse> =
        ApiResponse.ok(feedScrapUseCase.getExist(userId, feedId))

    @PostMapping("/{feedId}")
    fun create(
        @CurrentUser userId: String,
        @PathVariable feedId: String
    ): ApiResponse<Unit> =
        ApiResponse.ok(feedScrapUseCase.create(userId, feedId))

    @DeleteMapping("/{feedId}")
    fun delete(
        @CurrentUser userId: String,
        @PathVariable feedId: String
    ): ApiResponse<Unit> =
        ApiResponse.ok(feedScrapUseCase.delete(userId, feedId))
}
