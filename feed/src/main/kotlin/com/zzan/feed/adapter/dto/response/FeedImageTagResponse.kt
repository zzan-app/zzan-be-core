package com.zzan.feed.adapter.dto.response

import com.zzan.feed.domain.FeedImageTag

data class FeedImageTagResponse(
    val id: String?,
    val liquorId: String,
    val liquorName: String,
    val x: Double,
    val y: Double,
) {
    companion object {
        fun of(tag: FeedImageTag) = FeedImageTagResponse(
            id = tag.id,
            liquorId = tag.liquorId,
            liquorName = tag.liquorName,
            x = tag.x.value,
            y = tag.y.value,
        )
    }
}
