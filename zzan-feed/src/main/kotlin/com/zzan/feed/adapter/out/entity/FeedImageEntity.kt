package com.zzan.feed.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import com.zzan.feed.domain.FeedImage
import jakarta.persistence.*

@Entity
@Table(name = "feed_images")
class FeedImageEntity(
    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    val feed: FeedEntity,

    val imageUrl: String,

    @Column(name = "sort_order")
    val sortOrder: Int,

    @OneToMany(mappedBy = "image", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tags: MutableSet<FeedImageTagEntity> = mutableSetOf(),
) : BaseEntity(id) {
    fun toDomain(): FeedImage {
        return FeedImage(
            id = id,
            imageUrl = imageUrl,
            tags = tags.map { it.toDomain() },
        )
    }

    companion object {
        fun of(image: FeedImage, feed: FeedEntity, sortOrder: Int): FeedImageEntity {
            val entity = FeedImageEntity(
                id = image.id ?: UlidCreator.getUlid().toString(),
                feed = feed,
                imageUrl = image.imageUrl,
                sortOrder = sortOrder,
            )

            image.tags.forEach { tag ->
                entity.tags.add(FeedImageTagEntity.of(tag, entity))
            }

            return entity
        }
    }
}
