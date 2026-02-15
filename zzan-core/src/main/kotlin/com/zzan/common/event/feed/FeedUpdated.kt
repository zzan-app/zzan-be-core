package com.zzan.common.event.feed

data class FeedUpdated(
    val feedId: String,
    val score: Double,
    val kakaoPlaceId: String?,
    val address: String?,
    val phone: String?,
    val longitude: Double?,
    val latitude: Double?
)
