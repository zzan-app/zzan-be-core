package com.zzan.feed.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "feed_liquors",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["liquor_id", "feed_id"])
    ]
)
class FeedLiquorEntity(
    id: String = UlidCreator.getUlid().toString(),

    @Column(name = "feed_id")
    val feedId: String,

    @Column(name = "liquor_id")
    val liquorId: String,
) : BaseEntity(id) {

    companion object {
        fun of(feedId: String, liquorId: String) = FeedLiquorEntity(
            feedId = feedId,
            liquorId = liquorId,
        )
    }
}
