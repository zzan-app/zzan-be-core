package com.zzan.place.adapter.out

import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.application.port.out.PlaceRepository
import com.zzan.place.domain.Place
import com.zzan.place.domain.vo.ViewBox
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PlacePersistenceAdapter(
    private val placeJpaRepository: PlaceJpaRepository
) : PlaceRepository {
    override fun findById(placeId: String): Place? =
        placeJpaRepository.findByIdOrNull(placeId)?.toDomain()

    override fun getPlacesInViewBox(viewBox: ViewBox, request: PageRequest): List<PlaceInfoResponse> =
        placeJpaRepository.findPlacesByViewBox(viewBox, request)

    override fun findByKakaoPlaceId(kakaoPlaceId: String): Place? =
        placeJpaRepository.findByKakaoPlaceId(kakaoPlaceId)?.toDomain()

    override fun save(place: Place): Place =
        placeJpaRepository.save(PlaceEntity.of(place)).toDomain()

    override fun update(place: Place) {
        placeJpaRepository.findByIdOrNull(place.id!!)?.update(place)
    }
}
