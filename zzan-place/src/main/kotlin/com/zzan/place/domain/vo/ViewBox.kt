package com.zzan.place.domain.vo


data class ViewBox(
    val minLongitude: Longitude,
    val maxLongitude: Longitude,
    val minLatitude: Latitude,
    val maxLatitude: Latitude
) {
    init {
        require(minLongitude.value <= maxLongitude.value) {
            "최소 경도는 최대 경도보다 작거나 같아야 합니다"
        }
        require(minLatitude.value <= maxLatitude.value) {
            "최소 위도는 최대 위도보다 작거나 같아야 합니다"
        }
    }
}
