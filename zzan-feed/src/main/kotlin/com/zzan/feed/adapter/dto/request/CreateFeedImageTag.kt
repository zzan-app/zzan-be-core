package com.zzan.feed.adapter.dto.request

import com.zzan.feed.domain.FeedImageTag

data class CreateFeedImageTag(
    val liquorId: String,
    val liquorName: String,
    val x: Double,
    val y: Double,
) {
    fun toDomain() = FeedImageTag.of(
        liquorId = liquorId,
        liquorName = liquorName,
        x = x,
        y = y,
    )
}
