package com.zzan.feed.adapter.`in`

import com.zzan.common.annotation.CurrentUser
import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.IdResponse
import com.zzan.common.response.ApiResponse
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.adapter.dto.response.FeedDetailResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.`in`.FeedUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feeds")
class FeedController(
    private val feedUseCase: FeedUseCase,
) {
    @GetMapping("/{feedId}")
    fun getDetail(@PathVariable feedId: String): ApiResponse<FeedDetailResponse> =
        ApiResponse.ok(feedUseCase.getDetail(feedId))

    @GetMapping("/me")
    fun getMine(
        @CurrentUser userId: String,
        @Valid request: CursorPageRequest,
    ): ApiResponse<CursorPageResponse<FeedInfoResponse>> =
        ApiResponse.ok(feedUseCase.getByUser(userId, request))

    @GetMapping("/places/{kakaoPlaceId}")
    fun getByPlace(
        @PathVariable kakaoPlaceId: String,
        @Valid request: CursorPageRequest,
    ): ApiResponse<CursorPageResponse<FeedInfoResponse>> =
        ApiResponse.ok(feedUseCase.getByPlace(kakaoPlaceId, request))

    @GetMapping("/liquors/{liquorId}")
    fun getByLiquor(
        @PathVariable liquorId: String,
        @Valid request: CursorPageRequest
    ): ApiResponse<CursorPageResponse<FeedInfoResponse>> =
        ApiResponse.ok(feedUseCase.getByLiquor(liquorId, request))

    @GetMapping("/recent")
    fun getRecent(
        @Valid request: CursorPageRequest
    ): ApiResponse<CursorPageResponse<FeedInfoResponse>> =
        ApiResponse.ok(feedUseCase.getRecent(request))

    @PostMapping
    fun create(
        @CurrentUser userId: String,
        @Valid @RequestBody request: CreateFeed,
    ): ApiResponse<IdResponse> =
        ApiResponse.ok(feedUseCase.create(userId, request))

    @DeleteMapping("/{feedId}")
    fun delete(
        @CurrentUser userId: String,
        @PathVariable feedId: String,
    ): ApiResponse<Unit> =
        ApiResponse.ok(feedUseCase.delete(userId, feedId))
}
