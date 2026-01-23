package com.zzan.feed.adapter.dto.response

import com.zzan.common.annotation.ImageUrl

data class FeedInfoResponse(
    val id: String,
    @ImageUrl
    val imageUrl: String?,
    val score: Double?,
    val liquorCount: Int = 0,
    val userId: String,
    val userName: String?,
    @ImageUrl
    val userProfileImage: String?,
    val kakaoPlaceId: String?,
    val placeName: String?,
    val placeAddress: String?,
)
