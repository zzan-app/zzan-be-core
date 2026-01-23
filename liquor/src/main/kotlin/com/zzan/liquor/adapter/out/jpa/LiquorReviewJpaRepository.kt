package com.zzan.liquor.adapter.out.jpa

import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.adapter.out.entity.LiquorReviewEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LiquorReviewJpaRepository : JpaRepository<LiquorReviewEntity, String> {
    @Query(
        """
        SELECT new com.zzan.liquor.adapter.dto.response.LiquorReviewResponse(
            lr.id, lr.userId, lr.username, lr.userProfileImageUrl, 
            lr.liquorId, lr.liquorName,lr.score, lr.text, lr.createdAt
        )
        FROM LiquorReviewEntity lr
        WHERE lr.liquorId = :id
        AND (:cursor IS NULL OR lr.id <= :cursor)
        ORDER BY lr.id DESC
    """
    )
    fun getListByLiquorId(id: String, cursor: String?, pageable: Pageable): List<LiquorReviewResponse>
    fun findByUserIdAndLiquorId(userId: String, liquorId: String): LiquorReviewEntity?
    fun deleteByUserIdAndLiquorId(userId: String, liquorId: String): Int
}
