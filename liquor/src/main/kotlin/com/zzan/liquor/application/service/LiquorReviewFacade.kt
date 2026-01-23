package com.zzan.liquor.application.service

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.application.port.`in`.LiquorReviewUseCase
import com.zzan.liquor.application.port.out.LiquorUserInfoProvider
import org.springframework.stereotype.Service

@Service
class LiquorReviewFacade(
    private val liquorReviewService: LiquorReviewService,
    private val liquorUserInfoProvider: LiquorUserInfoProvider,
) : LiquorReviewUseCase {

    override fun find(userId: String, liquorId: String): LiquorReviewResponse? {
        return liquorReviewService.find(userId, liquorId)
    }

    override fun getList(liquorId: String, request: CursorPageRequest): CursorPageResponse<LiquorReviewResponse> {
        return liquorReviewService.getList(liquorId, request)
    }

    override fun create(userId: String, liquorId: String, request: CreateLiquorReview) {
        val reviewer = liquorUserInfoProvider.getBasicUserInfo(userId)
        liquorReviewService.create(reviewer, liquorId, request)
    }

    override fun update(userId: String, liquorId: String, request: UpdateLiquorReview) {
        liquorReviewService.update(userId, liquorId, request)
    }

    override fun delete(userId: String, liquorId: String) {
        liquorReviewService.delete(userId, liquorId)
    }
}
