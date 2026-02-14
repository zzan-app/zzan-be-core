package com.zzan.feed.adapter.out

import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.adapter.out.entity.FeedEntity
import com.zzan.feed.adapter.out.jpa.FeedJpaRepository
import com.zzan.feed.application.port.out.FeedRepository
import com.zzan.feed.domain.Feed
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class FeedPersistenceAdapter(
    private val feedJpaRepository: FeedJpaRepository,
) : FeedRepository {
    override fun save(feed: Feed): Feed =
        feedJpaRepository.save(FeedEntity.of(feed)).toDomain()

    override fun findById(feedId: String): Feed? =
        feedJpaRepository.findByIdWithImagesAndTags(feedId)?.toDomain()

    override fun findByKakaoPlaceId(kakaoPlaceId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse> =
        feedJpaRepository.findByKakaoPlaceId(kakaoPlaceId, cursor, pageable)

    override fun findByUserId(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse> =
        feedJpaRepository.findByUserId(userId, cursor, pageable)

    override fun findByLiquorId(liquorId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse> =
        feedJpaRepository.findByLiquorId(liquorId, cursor, pageable)

    override fun findRecentFeed(cursor: String?, pageable: Pageable): List<FeedInfoResponse> =
        feedJpaRepository.findRecentFeed(cursor, pageable)

    override fun delete(feedId: String) =
        feedJpaRepository.delete(feedId, Instant.now())
}
