package com.zzan.place.adapter.`in`

import com.zzan.common.response.ApiResponse
import com.zzan.place.adapter.dto.PlaceDetail
import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.adapter.dto.ViewBoxRequest
import com.zzan.place.application.port.`in`.PlaceUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/places")
class PlaceController(
    private val placeUseCase: PlaceUseCase,
) {
    @GetMapping
    fun getPlacesInViewBox(request: ViewBoxRequest): ApiResponse<List<PlaceInfoResponse>> =
        ApiResponse.ok(placeUseCase.getPlacesInViewBox(request))

    @GetMapping("/{placeId}")
    fun getDetail(@PathVariable placeId: String): ApiResponse<PlaceDetail> =
        ApiResponse.ok(placeUseCase.getPlaceDetail(placeId))

    @GetMapping("/kakao/{kakaoPlaceId}")
    fun getDetailByKakaoId(@PathVariable kakaoPlaceId: String): ApiResponse<PlaceDetail?> =
        ApiResponse.ok(placeUseCase.findPlaceDetailByKakaoId(kakaoPlaceId))
}
