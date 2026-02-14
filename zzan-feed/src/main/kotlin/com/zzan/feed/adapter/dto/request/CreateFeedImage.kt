package com.zzan.feed.adapter.dto.request

import com.zzan.feed.domain.FeedImage
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

class CreateFeedImage(
    @field:NotBlank(message = "이미지 URL이 존재하지 않습니다.")
    val imageUrl: String,
    @field:Valid
    val tags: List<CreateFeedImageTag>,
) {
    fun toDomain() = FeedImage.of(
        imageUrl = imageUrl,
        feedImageTagList = tags.map { it.toDomain() },
    )
}
