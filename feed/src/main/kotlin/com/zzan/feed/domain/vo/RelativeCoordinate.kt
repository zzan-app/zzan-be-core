package com.zzan.feed.domain.vo

data class RelativeCoordinate(
    val value: Double,
) {
    init {
        require(value in 0.0..1.0) { "상대 좌표는 0.0~1.0 사이여야 합니다: $value" }
    }
}
