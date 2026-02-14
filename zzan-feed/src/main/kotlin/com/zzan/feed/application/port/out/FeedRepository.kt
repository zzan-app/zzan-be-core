package com.zzan.feed.application.port.out

import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.domain.Feed
import org.springframework.data.domain.Pageable

interface FeedRepository {
    fun save(feed: Feed): Feed
    fun findById(feedId: String): Feed?
    fun findByKakaoPlaceId(kakaoPlaceId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>
    fun findByUserId(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>
    fun findByLiquorId(liquorId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>
    fun findRecentFeed(cursor: String?, pageable: Pageable): List<FeedInfoResponse>
    fun delete(feedId: String)
}
