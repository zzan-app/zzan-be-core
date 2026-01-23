package com.zzan.feed.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "feed_scraps",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "feed_id"])
    ]
)
class FeedScrapEntity(
    id: String = UlidCreator.getUlid().toString(),

    @Column(name = "user_id", length = 26)
    val userId: String, // 스크랩한 사용자 ID

    @Column(name = "feed_id", length = 26)
    val feedId: String, // 스크랩한 피드 ID
) : BaseEntity(id) {
    companion object {
        fun of(userId: String, feedId: String): FeedScrapEntity {
            return FeedScrapEntity(
                userId = userId,
                feedId = feedId
            )
        }
    }
}
