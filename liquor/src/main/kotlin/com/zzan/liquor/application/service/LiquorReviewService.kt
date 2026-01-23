package com.zzan.liquor.application.service

import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.exception.CustomException
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.application.port.out.LiquorRepository
import com.zzan.liquor.application.port.out.LiquorReviewRepository
import com.zzan.liquor.domain.LiquorReview
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LiquorReviewService(
    private val liquorReviewRepository: LiquorReviewRepository,
    private val liquorRepository: LiquorRepository,
    private val liquorScoreManager: LiquorScoreManager,
) {

    fun find(userId: String, liquorId: String): LiquorReviewResponse? {
        return liquorReviewRepository.find(userId, liquorId)
            ?.let(LiquorReviewResponse::of)
    }

    fun getList(liquorId: String, request: CursorPageRequest): CursorPageResponse<LiquorReviewResponse> {
        val items = liquorReviewRepository.getListByLiquorId(
            liquorId = liquorId,
            cursor = request.cursor,
            pageable = PageRequest.of(0, request.size + 1)
        )
        return CursorPageResponse.of(items, request.size) { it.id }
    }

    @Transactional
    fun create(reviewer: BaseUserInfo, liquorId: String, request: CreateLiquorReview) {
        val liquor = liquorRepository.findById(liquorId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 아이템입니다.")

        liquorReviewRepository.save(LiquorReview.of(reviewer, liquor, request))
        liquorScoreManager.addScore(liquorId, request.score)
    }

    @Transactional
    fun update(userId: String, liquorId: String, request: UpdateLiquorReview) {
        val review = liquorReviewRepository.find(userId, liquorId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "리뷰가 존재하지 않습니다.")

        liquorReviewRepository.update(review.update(request))
        liquorScoreManager.updateScore(liquorId, review.score, request.score)
    }

    @Transactional
    fun delete(userId: String, liquorId: String) {
        val review = liquorReviewRepository.find(userId, liquorId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "리뷰가 존재하지 않습니다.")

        liquorReviewRepository.delete(userId, liquorId)
        liquorScoreManager.removeScore(liquorId, review.score)
    }
}
