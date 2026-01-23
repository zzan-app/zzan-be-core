package com.zzan.liquor.application.port.out

import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.domain.LiquorReview
import org.springframework.data.domain.Pageable

interface LiquorReviewRepository {
    fun find(userId: String, liquorId: String): LiquorReview?
    fun getListByLiquorId(liquorId: String, cursor: String?, pageable: Pageable): List<LiquorReviewResponse>
    fun save(liquorReview: LiquorReview): LiquorReview
    fun update(liquorReview: LiquorReview)
    fun delete(userId: String, liquorId: String)
}
