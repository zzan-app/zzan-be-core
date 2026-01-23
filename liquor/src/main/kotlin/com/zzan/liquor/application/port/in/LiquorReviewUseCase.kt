package com.zzan.liquor.application.port.`in`

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse

interface LiquorReviewUseCase {
    fun find(userId: String, liquorId: String): LiquorReviewResponse?
    fun getList(liquorId: String, request: CursorPageRequest): CursorPageResponse<LiquorReviewResponse>
    fun create(userId: String, liquorId: String, request: CreateLiquorReview)
    fun update(userId: String, liquorId: String, request: UpdateLiquorReview)
    fun delete(userId: String, liquorId: String)
}
