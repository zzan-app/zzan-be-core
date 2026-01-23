package com.zzan.feed.application.service

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.IdResponse
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.adapter.dto.response.FeedDetailResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.`in`.FeedUseCase
import com.zzan.feed.application.port.out.FeedUserInfoProvider
import org.springframework.stereotype.Service

@Service
class FeedFacade(
    private val feedService: FeedService,
    private val userProvider: FeedUserInfoProvider,
) : FeedUseCase {

    override fun getDetail(feedId: String): FeedDetailResponse =
        feedService.getDetail(feedId)

    override fun getByPlace(kakaoPlaceId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> =
        feedService.getByPlace(kakaoPlaceId, request)

    override fun getByUser(userId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> =
        feedService.getByUser(userId, request)

    override fun getByLiquor(liquorId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> =
        feedService.getByLiquor(liquorId, request)

    override fun getRecent(request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> =
        feedService.getRecent(request)

    override fun create(userId: String, request: CreateFeed): IdResponse {
        val user = userProvider.getBasicUserInfo(userId)
        return feedService.create(user, request)
    }

    override fun delete(userId: String, feedId: String) {
        feedService.delete(userId, feedId)
    }
}
