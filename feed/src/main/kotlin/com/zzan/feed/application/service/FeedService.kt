package com.zzan.feed.application.service

import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.IdResponse
import com.zzan.common.exception.CustomException
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.adapter.dto.response.FeedDetailResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.out.FeedEventPublisher
import com.zzan.feed.application.port.out.FeedLiquorRepository
import com.zzan.feed.application.port.out.FeedRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FeedService(
    private val feedRepository: FeedRepository,
    private val feedLiquorRepository: FeedLiquorRepository,
    private val feedEventPublisher: FeedEventPublisher
) {

    fun getDetail(feedId: String): FeedDetailResponse {
        val feed = feedRepository.findById(feedId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "피드를 찾을 수 없습니다.")
        return FeedDetailResponse.of(feed)
    }

    fun getByPlace(kakaoPlaceId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> {
        val items = feedRepository.findByKakaoPlaceId(
            kakaoPlaceId = kakaoPlaceId,
            cursor = request.cursor,
            pageable = PageRequest.of(0, request.size + 1)
        )
        return CursorPageResponse.of(items, request.size) { it.id }
    }

    fun getByUser(userId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> {
        val items = feedRepository.findByUserId(
            userId = userId,
            cursor = request.cursor,
            pageable = PageRequest.of(0, request.size + 1)
        )
        return CursorPageResponse.of(items, request.size) { it.id }
    }

    fun getByLiquor(liquorId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> {
        val items = feedRepository.findByLiquorId(
            liquorId = liquorId,
            cursor = request.cursor,
            pageable = PageRequest.of(0, request.size + 1)
        )
        return CursorPageResponse.of(items, request.size) { it.id }
    }

    fun getRecent(request: CursorPageRequest): CursorPageResponse<FeedInfoResponse> {
        val items = feedRepository.findRecentFeed(
            cursor = request.cursor,
            pageable = PageRequest.of(0, request.size + 1)
        )
        return CursorPageResponse.of(items, request.size) { it.id }
    }

    @Transactional
    fun create(user: BaseUserInfo, request: CreateFeed): IdResponse {
        val feed = feedRepository.save(request.toDomain(user))

        val feedId = feed.id
            ?: throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "피드 생성에 실패했습니다.")

        val liquorIds = feed.images.flatMap { it.tags }.map { it.liquorId }.distinct()
        feedLiquorRepository.saveAll(feedId, liquorIds)
        feedEventPublisher.publish(feed.toCreatedEvent())

        return IdResponse(feedId)
    }

    @Transactional
    fun delete(userId: String, feedId: String) {
        val feed = feedRepository.findById(feedId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "피드를 찾을 수 없습니다.")

        if (feed.userId != userId)
            throw CustomException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.")

        feedRepository.delete(feedId)
        feedEventPublisher.publish(feed.toDeletedEvent())
    }
}
