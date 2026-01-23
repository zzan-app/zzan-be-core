package com.zzan.feed.adapter.out

import com.zzan.feed.adapter.out.entity.FeedLiquorEntity
import com.zzan.feed.adapter.out.jpa.FeedLiquorJpaRepository
import com.zzan.feed.application.port.out.FeedLiquorRepository
import org.springframework.stereotype.Repository

@Repository
class FeedLiquorPersistenceAdapter(
    private val feedLiquorJpaRepository: FeedLiquorJpaRepository,
) : FeedLiquorRepository {

    override fun saveAll(feedId: String, liquorIds: List<String>) {
        val entities = liquorIds.map { FeedLiquorEntity.of(feedId, it) }
        feedLiquorJpaRepository.saveAll(entities)
    }
}
