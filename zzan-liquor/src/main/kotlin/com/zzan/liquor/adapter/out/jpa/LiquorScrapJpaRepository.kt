package com.zzan.liquor.adapter.out.jpa

import com.zzan.liquor.adapter.out.entity.LiquorEntity
import com.zzan.liquor.adapter.out.entity.LiquorScrapEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LiquorScrapJpaRepository : JpaRepository<LiquorScrapEntity, String> {
    @Query(
        """
            SELECT l
            FROM LiquorScrapEntity ls
            JOIN LiquorEntity l ON ls.liquorId = l.id
            WHERE ls.userId = :userId
            AND (:cursor IS NULL OR l.id <= :cursor)
            ORDER BY l.id DESC
        """
    )
    fun findByUserId(
        @Param("userId") userId: String,
        @Param("cursor") cursor: String?,
        pageable: Pageable
    ): List<LiquorEntity>

    fun existsByUserIdAndLiquorId(userId: String, liquorId: String): Boolean

    fun deleteByUserIdAndLiquorId(userId: String, liquorId: String): Int
}

