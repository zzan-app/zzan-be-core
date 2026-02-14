package com.zzan.liquor.domain

import com.zzan.common.dto.BaseUserInfo
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import java.time.Instant

data class LiquorReview(
    val id: String? = null,
    val userId: String,
    val username: String?,
    val userProfileImageUrl: String?,

    val liquorId: String,
    val liquorName: String,

    val score: Double,
    val text: String?,
    val createdAt: Instant? = null,
) {
    fun update(request: UpdateLiquorReview): LiquorReview {
        return copy(
            score = request.score,
            text = request.text,
        )
    }

    companion object {
        fun of(
            reviewer: BaseUserInfo,
            liquor: Liquor,
            request: CreateLiquorReview,
        ) = LiquorReview(
            userId = reviewer.id,
            username = reviewer.name,
            userProfileImageUrl = reviewer.profileImage,
            liquorId = liquor.id!!,
            liquorName = liquor.name,
            score = request.score,
            text = request.text,
        )
    }
}
