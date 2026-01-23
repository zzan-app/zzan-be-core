package com.zzan.feed.adapter.out.jpa

import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.adapter.out.entity.FeedEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.Instant

interface FeedJpaRepository : JpaRepository<FeedEntity, String> {
    @Query(
        """
        SELECT DISTINCT f FROM FeedEntity f
        LEFT JOIN FETCH f.images i
        LEFT JOIN FETCH i.tags
        WHERE f.id = :id AND f.deletedAt IS NULL
    """
    )
    fun findByIdWithImagesAndTags(id: String): FeedEntity?

    @Query(
        """
        SELECT new com.zzan.feed.adapter.dto.response.FeedInfoResponse(
            f.id, f.imageUrl, f.score, f.liquorCount, f.userId, f.userName, 
            f.userProfileImageUrl, f.kakaoPlaceId, f.placeName, f.placeAddress
        )
        FROM FeedEntity f
        WHERE f.kakaoPlaceId = :kakaoPlaceId
        AND f.deletedAt IS NULL
        AND (:cursor IS NULL OR f.id <= :cursor)
        ORDER BY f.id DESC
    """
    )
    fun findByKakaoPlaceId(kakaoPlaceId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>

    @Query(
        """
        SELECT new com.zzan.feed.adapter.dto.response.FeedInfoResponse(
            f.id, f.imageUrl, f.score, f.liquorCount, f.userId, f.userName, 
            f.userProfileImageUrl, f.kakaoPlaceId, f.placeName, f.placeAddress
        )
        FROM FeedEntity f
        WHERE f.userId = :userId
        AND f.deletedAt IS NULL
        AND (:cursor IS NULL OR f.id <= :cursor)
        ORDER BY f.id DESC
    """
    )
    fun findByUserId(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>

    @Query(
        """
        SELECT new com.zzan.feed.adapter.dto.response.FeedInfoResponse(
            f.id, f.imageUrl, f.score, f.liquorCount, f.userId, f.userName, 
            f.userProfileImageUrl, f.kakaoPlaceId, f.placeName, f.placeAddress
        )
        FROM FeedEntity f
        WHERE f.deletedAt IS NULL
        AND (:cursor IS NULL OR f.id <= :cursor)
        ORDER BY f.id DESC
    """
    )
    fun findRecentFeed(cursor: String?, pageable: Pageable): List<FeedInfoResponse>

    @Query(
        """
        SELECT new com.zzan.feed.adapter.dto.response.FeedInfoResponse(
            f.id, f.imageUrl, f.score, f.liquorCount, f.userId, f.userName, 
            f.userProfileImageUrl, f.kakaoPlaceId, f.placeName, f.placeAddress
        )
        FROM FeedEntity f
        JOIN FeedLiquorEntity fl ON fl.feedId = f.id
        WHERE fl.liquorId = :liquorId
        AND f.deletedAt IS NULL
        AND (:cursor IS NULL OR f.id <= :cursor)
        ORDER BY f.id DESC
    """
    )
    fun findByLiquorId(liquorId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse>

    @Modifying
    @Query("UPDATE FeedEntity f SET f.deletedAt = :now WHERE f.id = :id")
    fun delete(id: String, now: Instant)
}
