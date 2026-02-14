package com.zzan.feed.application.port.`in`

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.IdResponse
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.adapter.dto.response.FeedDetailResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse

interface FeedUseCase {
    fun getDetail(feedId: String): FeedDetailResponse
    fun getByPlace(kakaoPlaceId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse>
    fun getByUser(userId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse>
    fun getByLiquor(liquorId: String, request: CursorPageRequest): CursorPageResponse<FeedInfoResponse>
    fun getRecent(request: CursorPageRequest): CursorPageResponse<FeedInfoResponse>
    fun create(userId: String, request: CreateFeed): IdResponse
    fun delete(userId: String, feedId: String)
}
