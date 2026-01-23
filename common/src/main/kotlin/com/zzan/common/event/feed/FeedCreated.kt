package com.zzan.common.event.feed


data class FeedCreated(
    val feedId: String,
    val score: Double?,

    val userId: String,

    val kakaoPlaceId: String?,
    val placeName: String?,
    val placeAddress: String?,
    val placePhone: String?,

    val longitude: Double?,
    val latitude: Double?
)
