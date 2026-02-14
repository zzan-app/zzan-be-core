package com.zzan.feed.adapter.dto.response

import com.zzan.common.annotation.ImageUrl
import com.zzan.feed.domain.FeedImage

data class FeedImageResponse(
    val id: String?,
    @ImageUrl
    val imageUrl: String,
    val tags: List<FeedImageTagResponse>,
) {
    companion object {
        fun of(image: FeedImage) = FeedImageResponse(
            id = image.id,
            imageUrl = image.imageUrl,
            tags = image.tags.map { FeedImageTagResponse.of(it) },
        )
    }
}
