package com.zzan.feed.adapter.out.jpa

import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.adapter.out.entity.FeedScrapEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FeedScrapJpaRepository : JpaRepository<FeedScrapEntity, String> {
    @Query(
        """
        SELECT new com.zzan.feed.adapter.dto.response.FeedInfoResponse(
            f.id, f.imageUrl, f.score, f.liquorCount, f.userId, f.userName, 
            f.userProfileImageUrl, f.kakaoPlaceId, f.placeName, f.placeAddress
        )
        FROM FeedEntity f
        JOIN FeedScrapEntity fs ON f.id = fs.feedId
        WHERE fs.userId = :userId
        AND (:cursor IS NULL OR f.id <= :cursor)
        ORDER BY f.id DESC
    """
    )
    fun findFeedsByUserId(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>

    fun existsByUserIdAndFeedId(userId: String, feedId: String): Boolean

    fun deleteByUserIdAndFeedId(userId: String, feedId: String): Int
}
