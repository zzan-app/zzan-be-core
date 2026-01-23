package com.zzan.place.application.service

import com.zzan.common.exception.CustomException
import com.zzan.place.adapter.dto.PlaceDetail
import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.adapter.dto.ViewBoxRequest
import com.zzan.place.application.port.`in`.PlaceUseCase
import com.zzan.place.application.port.out.PlaceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PlaceService(
    private val placeRepository: PlaceRepository
) : PlaceUseCase {
    override fun getPlacesInViewBox(request: ViewBoxRequest): List<PlaceInfoResponse> {
        return placeRepository.getPlacesInViewBox(
            request.toViewBox(),
            PageRequest.of(
                0, 20,
            )
        )
    }

    override fun getPlaceDetail(placeId: String): PlaceDetail {
        val place = placeRepository.findById(placeId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 장소입니다.")
        return PlaceDetail.of(place)
    }

    override fun findPlaceDetailByKakaoId(kakaoPlaceId: String): PlaceDetail? {
        return placeRepository.findByKakaoPlaceId(kakaoPlaceId)?.let { PlaceDetail.of(it) }
    }
}
