package com.zzan.feed.adapter.out


import com.zzan.feed.adapter.dto.response.FeedInfoResponse
import com.zzan.feed.adapter.out.entity.FeedScrapEntity
import com.zzan.feed.adapter.out.jpa.FeedScrapJpaRepository
import com.zzan.feed.application.port.out.FeedScrapRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository


@Repository
class FeedScrapPersistenceAdapter(
    private val feedScrapJpaRepository: FeedScrapJpaRepository
) : FeedScrapRepository {
    override fun save(userId: String, feedId: String) {
        feedScrapJpaRepository.save(FeedScrapEntity.of(userId, feedId))
    }

    override fun getFeeds(userId: String, cursor: String?, pageable: Pageable): List<FeedInfoResponse> =
        feedScrapJpaRepository.findFeedsByUserId(userId, cursor, pageable)

    override fun getExist(userId: String, feedId: String): Boolean =
        feedScrapJpaRepository.existsByUserIdAndFeedId(userId, feedId)

    override fun delete(userId: String, feedId: String) {
        feedScrapJpaRepository.deleteByUserIdAndFeedId(userId, feedId)
    }
}
