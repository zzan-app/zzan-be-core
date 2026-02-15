package com.zzan.common.event.liquor

data class LiquorReviewUpdated(
    val liquorId: String,
    val userId: String,
    val oldScore: Double,
    val newScore: Double,
)
