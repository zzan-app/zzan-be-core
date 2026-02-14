package com.zzan.feed.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import com.zzan.feed.domain.FeedImageTag
import com.zzan.feed.domain.vo.RelativeCoordinate
import jakarta.persistence.*

@Entity
@Table(name = "feed_image_tags")
class FeedImageTagEntity(
    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    val image: FeedImageEntity,

    @Column(name = "liquor_id", length = 26)
    val liquorId: String,

    @Column(name = "liquor_name")
    val liquorName: String,


    @Column(name = "x_pos")
    val x: Double,

    @Column(name = "y_pos")
    val y: Double,
) : BaseEntity(id) {
    fun toDomain(): FeedImageTag {
        return FeedImageTag(
            id = id,
            liquorId = liquorId,
            liquorName = liquorName,
            x = RelativeCoordinate(x),
            y = RelativeCoordinate(y),
        )
    }

    companion object {
        fun of(tag: FeedImageTag, feedImage: FeedImageEntity) = FeedImageTagEntity(
            id = tag.id ?: UlidCreator.getUlid().toString(),
            image = feedImage,
            liquorId = tag.liquorId,
            liquorName = tag.liquorName,
            x = tag.x.value,
            y = tag.y.value,
        )
    }
}
