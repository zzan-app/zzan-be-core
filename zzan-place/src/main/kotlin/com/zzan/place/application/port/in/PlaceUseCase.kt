package com.zzan.place.application.port.`in`

import com.zzan.place.adapter.dto.PlaceDetail
import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.adapter.dto.ViewBoxRequest

interface PlaceUseCase {
    fun getPlacesInViewBox(request: ViewBoxRequest): List<PlaceInfoResponse>
    fun findPlaceDetailByKakaoId(kakaoPlaceId: String): PlaceDetail?
    fun getPlaceDetail(placeId: String): PlaceDetail
}
