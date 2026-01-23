package com.zzan.feed.domain

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import com.zzan.feed.domain.vo.Score
import java.time.Instant

data class Feed(
    val id: String? = null,

    val userId: String,
    val userName: String? = null,
    val userProfileImage: String? = null,

    val imageUrl: String? = null,
    val images: List<FeedImage>,

    val score: Score? = null,
    val liquorCount: Int = 0,
    val text: String,

    val kakaoPlaceId: String? = null,
    val placeName: String? = null,
    val placePhone: String? = null,
    val placeAddress: String? = null,

    val longitude: Double? = null,
    val latitude: Double? = null,

    val createdAt: Instant? = null,
) {
    fun toCreatedEvent(): FeedCreated {
        return FeedCreated(
            feedId = id!!,
            score = score?.value,
            userId = userId,
            kakaoPlaceId = kakaoPlaceId,
            placeName = placeName,
            placeAddress = placeAddress,
            placePhone = placePhone,
            longitude = longitude,
            latitude = latitude,
        )
    }

    fun toDeletedEvent(): FeedDeleted {
        return FeedDeleted(
            feedId = id!!,
            score = score?.value,
            userId = userId,
            kakaoPlaceId = kakaoPlaceId
        )
    }

}
