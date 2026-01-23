package com.zzan.liquor.adapter.out

import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.adapter.out.entity.LiquorReviewEntity
import com.zzan.liquor.adapter.out.jpa.LiquorReviewJpaRepository
import com.zzan.liquor.application.port.out.LiquorReviewRepository
import com.zzan.liquor.domain.LiquorReview
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class LiquorReviewPersistenceAdapter(
    private val liquorReviewJpaRepository: LiquorReviewJpaRepository
) : LiquorReviewRepository {

    override fun find(userId: String, liquorId: String): LiquorReview? {
        return liquorReviewJpaRepository.findByUserIdAndLiquorId(userId, liquorId)?.toDomain()
    }

    override fun getListByLiquorId(liquorId: String, cursor: String?, pageable: Pageable): List<LiquorReviewResponse> {
        return liquorReviewJpaRepository.getListByLiquorId(liquorId, cursor, pageable)
    }

    override fun save(liquorReview: LiquorReview): LiquorReview {
        return liquorReviewJpaRepository.save(LiquorReviewEntity.of(liquorReview)).toDomain()
    }

    override fun update(liquorReview: LiquorReview) {
        liquorReviewJpaRepository.findByIdOrNull(liquorReview.id!!)?.update(liquorReview)
    }

    override fun delete(userId: String, liquorId: String) {
        liquorReviewJpaRepository.deleteByUserIdAndLiquorId(userId, liquorId)
    }
}
