package com.zzan.feed.domain

data class FeedImage(
    val id: String? = null,
    val imageUrl: String,
    val tags: List<FeedImageTag>
) {
    companion object {
        fun of(
            imageUrl: String,
            feedImageTagList: List<FeedImageTag>,
        ) = FeedImage(
            imageUrl = imageUrl,
            tags = feedImageTagList,
        )
    }
}
