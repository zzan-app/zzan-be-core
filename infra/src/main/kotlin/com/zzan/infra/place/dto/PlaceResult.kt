package com.zzan.infra.place.dto

data class PlaceResult(
    val id: String,
    val placeName: String,
    val categoryName: String,
    val phone: String,
    val addressName: String,
    val roadAddressName: String,
    val longitude: Double,
    val latitude: Double,
) {
    companion object {
        fun of(doc: KakaoPlaceDocument) = PlaceResult(
            id = doc.id,
            placeName = doc.placeName,
            categoryName = doc.categoryName,
            phone = doc.phone,
            addressName = doc.addressName,
            roadAddressName = doc.roadAddressName,
            longitude = doc.x.toDouble(),
            latitude = doc.y.toDouble(),
        )
    }
}
