package com.zzan.feed.application.service

import com.zzan.core.dto.CursorPageRequest
import com.zzan.core.dto.CursorPageResponse
import com.zzan.core.dto.IdResponse
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.adapter.dto.response.FeedDetailResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.`in`.FeedUseCase
import com.zzan.feed.application.port.out.FeedUserInfoProvider
import com.zzan.feed.application.port.out.FeedViewCountRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class FeedFacade(
    private val feedService: FeedService,
    private val userProvider: FeedUserInfoProvider,
    private val feedViewCountRepository: FeedViewCountRepository
) : FeedUseCase {
    private val logger = KotlinLogging.logger {}

    override fun getDetail(feedId: String): FeedDetailResponse {
        runCatching { feedViewCountRepository.increment(feedId) }
            .onFailure { e -> logger.warn(e) { "조회수 증가 요청 실패 feedId: $feedId" } }
        return feedService.getDetail(feedId)
    }

    override fun syncViewCounts() {
        val counts = feedViewCountRepository.getAllAndClear()
        counts.forEach { (feedId, count) ->
            feedService.syncViewCount(feedId, count)
        }
    }

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
