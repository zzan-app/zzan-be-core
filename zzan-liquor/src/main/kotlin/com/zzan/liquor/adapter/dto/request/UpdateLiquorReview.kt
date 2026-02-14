package com.zzan.liquor.adapter.dto.request

import jakarta.validation.constraints.NotNull

data class UpdateLiquorReview(
    @field:NotNull(message = "점수는 필수로 입력해야 합니다.")
    val score: Double,
    
    val text: String? = null,
)
