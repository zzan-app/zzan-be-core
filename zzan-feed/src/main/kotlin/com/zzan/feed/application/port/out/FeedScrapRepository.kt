package com.zzan.feed.application.port.out

import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import org.springframework.data.domain.Pageable

interface FeedScrapRepository {
    fun getFeeds(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>
    fun save(userId: String, feedId: String)
    fun getExist(userId: String, feedId: String): Boolean
    fun delete(userId: String, feedId: String)
}
