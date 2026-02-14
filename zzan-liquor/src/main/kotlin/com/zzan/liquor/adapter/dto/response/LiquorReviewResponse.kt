package com.zzan.liquor.adapter.dto.response

import com.zzan.common.annotation.ImageUrl
import com.zzan.liquor.domain.LiquorReview
import java.time.Instant

data class LiquorReviewResponse(
    val id: String? = null,
    val userId: String,
    val username: String?,

    @ImageUrl
    val userProfileImageUrl: String?,

    val liquorId: String,
    val liquorName: String,

    val score: Double,
    val text: String?,
    val createdAt: Instant? = null,
) {
    companion object {
        fun of(review: LiquorReview) = LiquorReviewResponse(
            id = review.id,
            userId = review.userId,
            username = review.username,
            userProfileImageUrl = review.userProfileImageUrl,
            liquorId = review.liquorId,
            liquorName = review.liquorName,
            score = review.score,
            text = review.text,
            createdAt = review.createdAt,
        )
    }
}
