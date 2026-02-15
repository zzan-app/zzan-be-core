package com.zzan.infra.place.dto

data class KakaoPlaceSearchResult(
    val meta: KakaoPlaceMeta,
    val documents: List<KakaoPlaceDocument>
)
