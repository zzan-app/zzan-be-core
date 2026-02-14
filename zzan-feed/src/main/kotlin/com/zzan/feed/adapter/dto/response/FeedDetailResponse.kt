package com.zzan.feed.adapter.dto.response

import com.zzan.common.annotation.ImageUrl
import com.zzan.feed.domain.Feed
import java.time.Instant

data class FeedDetailResponse(
    val id: String? = null,

    val userId: String,
    val userName: String? = null,
    @ImageUrl
    val userProfileImage: String? = null,

    @ImageUrl
    val imageUrl: String? = null,
    val images: List<FeedImageResponse>,

    val score: Double? = null,
    val liquorCount: Int = 0,
    val text: String,

    val kakaoPlaceId: String? = null,
    val placeName: String? = null,
    val placePhone: String? = null,
    val placeAddress: String? = null,

    val createdAt: Instant? = null,
) {
    companion object {
        fun of(feed: Feed): FeedDetailResponse {
            return FeedDetailResponse(
                id = feed.id,

                userId = feed.userId,
                userName = feed.userName,
                userProfileImage = feed.userProfileImage,

                imageUrl = feed.imageUrl,
                images = feed.images.map { FeedImageResponse.of(it) },

                score = feed.score?.value,
                liquorCount = feed.liquorCount,
                text = feed.text,

                kakaoPlaceId = feed.kakaoPlaceId,
                placeName = feed.placeName,
                placePhone = feed.placePhone,
                placeAddress = feed.placeAddress,

                createdAt = feed.createdAt,
            )
        }
    }
}
