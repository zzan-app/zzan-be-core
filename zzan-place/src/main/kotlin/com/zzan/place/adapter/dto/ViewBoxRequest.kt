package com.zzan.place.adapter.dto

import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude
import com.zzan.place.domain.vo.ViewBox


data class ViewBoxRequest(
    val minLongitude: Double,
    val maxLongitude: Double,
    val minLatitude: Double,
    val maxLatitude: Double
) {
    fun toViewBox(): ViewBox = ViewBox(
        minLongitude = Longitude(minLongitude),
        maxLongitude = Longitude(maxLongitude),
        minLatitude = Latitude(minLatitude),
        maxLatitude = Latitude(maxLatitude)
    )
}
