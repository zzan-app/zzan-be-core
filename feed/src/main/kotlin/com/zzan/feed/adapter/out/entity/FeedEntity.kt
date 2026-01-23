package com.zzan.feed.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.AuditableEntity
import com.zzan.feed.domain.Feed
import com.zzan.feed.domain.vo.Score
import jakarta.persistence.*

@Entity
@Table(name = "feeds")
class FeedEntity(
    id: String,

    @Version
    var version: Long = 0,


    @Column(name = "user_id", length = 26)
    val userId: String,

    @Column(name = "user_name")
    var userName: String? = null,

    @Column(name = "user_profile_image_url")
    var userProfileImageUrl: String? = null,


    @Column(name = "image_url")
    val imageUrl: String? = null,

    val score: Double? = null,

    @Column(name = "liquor_count")
    val liquorCount: Int = 0,

    @Column(columnDefinition = "TEXT")
    val text: String,

    @OrderBy("sortOrder ASC")
    @OneToMany(mappedBy = "feed", cascade = [CascadeType.ALL], orphanRemoval = true)
    val images: MutableSet<FeedImageEntity> = mutableSetOf(),


    @Column(name = "place_id", length = 26)
    val kakaoPlaceId: String? = null,

    @Column(name = "place_name")
    var placeName: String? = null,

    @Column(name = "place_address")
    var placeAddress: String? = null,

    val longitude: Double? = null,
    val latitude: Double? = null,
) : AuditableEntity(id) {

    fun toDomain(): Feed {
        return Feed(
            id = id,

            userId = userId,
            userName = userName,
            userProfileImage = userProfileImageUrl,

            imageUrl = imageUrl,
            images = images.map { it.toDomain() },
            score = score?.let { Score(it) },
            liquorCount = liquorCount,
            text = text,

            kakaoPlaceId = kakaoPlaceId,
            placeName = placeName,
            placeAddress = placeAddress,
            createdAt = createdAt,

            longitude = longitude,
            latitude = latitude,
        )
    }

    companion object {
        fun of(feed: Feed): FeedEntity {
            val entity = FeedEntity(
                id = feed.id ?: UlidCreator.getUlid().toString(),
                userId = feed.userId,
                userName = feed.userName,
                userProfileImageUrl = feed.userProfileImage,
                imageUrl = feed.imageUrl,
                score = feed.score?.value,
                liquorCount = feed.liquorCount,
                text = feed.text,
                kakaoPlaceId = feed.kakaoPlaceId,
                placeName = feed.placeName,
                placeAddress = feed.placeAddress,
            )

            feed.images.forEachIndexed { index, image ->
                entity.images.add(FeedImageEntity.of(image, entity, index))
            }

            return entity
        }
    }
}
