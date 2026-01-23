package com.zzan.liquor.domain.vo

import com.fasterxml.jackson.annotation.JsonValue

data class Score(
    @JsonValue
    val value: Double
) {
    init {
        require(value in 0.0..5.0) { "점수는 0.0~5.0 사이여야 합니다: $value" }
    }
}
