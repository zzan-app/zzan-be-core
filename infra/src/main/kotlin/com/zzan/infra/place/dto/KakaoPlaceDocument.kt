package com.zzan.infra.place.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoPlaceDocument(
    val id: String,
    @JsonProperty("place_name")
    val placeName: String,
    @JsonProperty("category_name")
    val categoryName: String,
    @JsonProperty("category_group_code")
    val categoryGroupCode: String,
    @JsonProperty("category_group_name")
    val categoryGroupName: String,
    val phone: String,
    @JsonProperty("address_name")
    val addressName: String,
    @JsonProperty("road_address_name")
    val roadAddressName: String,
    val x: String,
    val y: String,
    @JsonProperty("place_url")
    val placeUrl: String,
    val distance: String?
)
