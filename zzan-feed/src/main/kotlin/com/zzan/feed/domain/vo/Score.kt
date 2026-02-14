package com.zzan.feed.domain.vo

data class Score(
    val value: Double
) {
    init {
        require(value in 0.0..5.0) { "점수는 0.0~5.0 사이여야 합니다: $value" }
    }
}
