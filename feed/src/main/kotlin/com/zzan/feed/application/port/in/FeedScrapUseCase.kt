package com.zzan.feed.application.port.`in`

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.ExistResponse
import com.zzan.feed.adapter.dto.response.FeedInfoResponse

interface FeedScrapUseCase {
    fun getList(userId: String, pageRequest: CursorPageRequest): CursorPageResponse<FeedInfoResponse>
    fun getExist(userId: String, feedId: String): ExistResponse
    fun create(userId: String, feedId: String)
    fun delete(userId: String, feedId: String)
}
