package com.zzan.core.event.liquor

data class LiquorReviewDeleted(
    val liquorId: String,
    val userId: String,
    val score: Double,
)
