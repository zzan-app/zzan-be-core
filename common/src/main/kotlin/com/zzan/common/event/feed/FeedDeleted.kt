package com.zzan.common.event.feed

data class FeedDeleted(
    val feedId: String,
    val score: Double?,
    val userId: String,
    val kakaoPlaceId: String?
)
