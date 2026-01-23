package com.zzan.common.event.liquor

data class LiquorReviewDeleted(
    val liquorId: String,
    val userId: String,
    val score: Double,
)
