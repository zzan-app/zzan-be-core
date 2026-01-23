package com.zzan.place.adapter.dto

data class PlaceInfoResponse(
    val id: String,
    val name: String,

    val feedCount: Int,
    val score: Double?,

    val address: String,
    val phone: String?,

    val longitude: Double,
    val latitude: Double
)
