package com.zzan.infra.place.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoPlaceMeta(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("is_end")
    val isEnd: Boolean
)
