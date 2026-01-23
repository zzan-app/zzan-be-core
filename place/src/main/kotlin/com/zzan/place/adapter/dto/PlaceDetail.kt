package com.zzan.place.adapter.dto

import com.zzan.place.domain.Place

class PlaceDetail(
    val id: String,

    val name: String? = null,
    val averageScore: Double? = null,
    val feedCount: Int = 0,

    val kakaoPlaceId: String,
    val address: String?,
    val phone: String? = null,

    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        fun of(place: Place): PlaceDetail {
            return PlaceDetail(
                id = place.id!!,
                name = place.name,
                averageScore = place.averageScore,
                feedCount = place.feedCount,
                kakaoPlaceId = place.kakaoPlaceId,
                address = place.address,
                phone = place.phone,
                longitude = place.longitude.value,
                latitude = place.latitude.value,
            )
        }
    }
}
