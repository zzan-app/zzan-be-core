package com.zzan.place.application.port.out

import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.domain.Place
import com.zzan.place.domain.vo.ViewBox
import org.springframework.data.domain.PageRequest

interface PlaceRepository {
    fun findById(placeId: String): Place?
    fun findByKakaoPlaceId(kakaoPlaceId: String): Place?
    fun getPlacesInViewBox(viewBox: ViewBox, request: PageRequest): List<PlaceInfoResponse>
    fun save(place: Place): Place
    fun update(place: Place)
}
