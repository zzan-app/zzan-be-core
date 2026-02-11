package com.zzan.user.domain.vo

import java.time.LocalDate

data class Birth(
    val value: LocalDate,
) {
    init {
        require(value.isBefore(LocalDate.now())) { "생일은 현재 날짜 이전이어야 합니다: $value" }
    }
}
