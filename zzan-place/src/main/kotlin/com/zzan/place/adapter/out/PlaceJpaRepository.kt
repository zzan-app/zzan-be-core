package com.zzan.place.adapter.out

import com.zzan.place.adapter.dto.PlaceInfoResponse
import com.zzan.place.domain.vo.ViewBox
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceJpaRepository : JpaRepository<PlaceEntity, String> {
    @Query(
        """
            SELECT new com.zzan.place.adapter.dto.PlaceInfoResponse(
                p.id, p.name, p.feedCount, p.averageScore,
                p.address, p.phone, p.longitude, p.latitude
            )
            FROM PlaceEntity p 
            WHERE function('ST_Within', p.location, 
                   function('ST_MakeEnvelope', 
                       :#{#viewBox.minLongitude.value}, 
                       :#{#viewBox.minLatitude.value}, 
                       :#{#viewBox.maxLongitude.value}, 
                       :#{#viewBox.maxLatitude.value}, 
                       4326)) = true
           ORDER BY p.feedCount DESC
        """
    )
    fun findPlacesByViewBox(
        @Param("viewBox") viewBox: ViewBox,
        pageable: Pageable
    ): List<PlaceInfoResponse>

    fun findByKakaoPlaceId(kakaoPlaceId: String): PlaceEntity?
}
