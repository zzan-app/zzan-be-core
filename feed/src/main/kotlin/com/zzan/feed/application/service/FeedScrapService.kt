package com.zzan.feed.application.service

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.ExistResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.application.port.`in`.FeedScrapUseCase
import com.zzan.feed.application.port.out.FeedScrapRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FeedScrapService(
    private val feedScrapRepository: FeedScrapRepository,
) : FeedScrapUseCase {
    override fun getList(userId: String, pageRequest: CursorPageRequest): CursorPageResponse<FeedInfoResponse> {
        val items = feedScrapRepository.getFeeds(
            userId = userId,
            cursor = pageRequest.cursor,
            pageable = PageRequest.of(0, pageRequest.size + 1)
        )
        return CursorPageResponse.of(items, pageRequest.size) { it.id }
    }

    override fun getExist(userId: String, feedId: String): ExistResponse =
        ExistResponse(feedScrapRepository.getExist(userId, feedId))

    @Transactional
    override fun create(userId: String, feedId: String) {
        feedScrapRepository.save(userId, feedId)
    }

    @Transactional
    override fun delete(userId: String, feedId: String) {
        feedScrapRepository.delete(userId, feedId)
    }
}
