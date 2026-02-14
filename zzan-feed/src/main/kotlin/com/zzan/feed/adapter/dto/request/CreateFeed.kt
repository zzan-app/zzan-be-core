package com.zzan.feed.adapter.dto.request

import com.zzan.common.dto.BaseUserInfo
import com.zzan.feed.domain.Feed
import com.zzan.feed.domain.vo.Score
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class CreateFeed(
    val score: Double?,

    val text: String,

    @field:Valid
    @field:NotEmpty
    val images: List<CreateFeedImage>,

    val kakaoPlaceId: String?,
    val placeName: String?,
    val placePhone: String?,
    val placeAddress: String?,

    val longitude: Double?,
    val latitude: Double?,
) {
    fun toDomain(user: BaseUserInfo): Feed {
        val images = images.map { it.toDomain() }
        return Feed(
            userId = user.id,
            userName = user.name,
            userProfileImage = user.profileImage,

            imageUrl = images.firstOrNull()?.imageUrl,
            images = images,
            liquorCount = images.sumOf { it.tags.size },
            score = score?.let { Score(it) },
            text = text,

            kakaoPlaceId = kakaoPlaceId,
            placeName = placeName,
            placePhone = placePhone,
            placeAddress = placeAddress,

            longitude = longitude,
            latitude = latitude,
        )
    }
}
