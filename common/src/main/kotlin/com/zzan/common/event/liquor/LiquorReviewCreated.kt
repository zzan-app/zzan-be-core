package com.zzan.common.event.liquor

data class LiquorReviewCreated(
    val liquorId: String,
    val userId: String,
    val score: Double,
)
