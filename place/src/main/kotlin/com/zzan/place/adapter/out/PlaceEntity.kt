package com.zzan.place.adapter.out

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import com.zzan.place.domain.Place
import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.locationtech.jts.geom.Point

@Entity
@Table(name = "places")
class PlaceEntity(
    id: String,

    @Version
    var version: Long = 0,

    @Column(name = "average_score")
    var averageScore: Double? = null,
    @Column(name = "feed_count")
    var feedCount: Int = 0,
    @Column(name = "total_score")
    var totalScore: Double = 0.0,

    @Column(name = "kakao_place_id", unique = true)
    val kakaoPlaceId: String,

    val name: String?,
    val address: String?,
    val phone: String? = null,

    val longitude: Double,
    val latitude: Double,

    @Column(
        columnDefinition = "geometry(Point, 4326) GENERATED ALWAYS AS (ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)) STORED",
        insertable = false,
        updatable = false
    )
    val location: Point? = null,
) : BaseEntity(id) {
    fun toDomain(): Place {
        return Place(
            id = id,
            name = name,
            averageScore = averageScore,
            feedCount = feedCount,
            totalScore = totalScore,
            kakaoPlaceId = kakaoPlaceId,
            address = address,
            phone = phone,
            longitude = Longitude(longitude),
            latitude = Latitude(latitude),
        )
    }

    fun update(place: Place) {
        this.averageScore = place.averageScore
        this.feedCount = place.feedCount
        this.totalScore = place.totalScore
    }

    companion object {
        fun of(place: Place): PlaceEntity {
            return PlaceEntity(
                id = place.id ?: UlidCreator.getUlid().toString(),
                name = place.name,
                averageScore = place.averageScore,
                feedCount = place.feedCount,
                totalScore = place.totalScore,
                kakaoPlaceId = place.kakaoPlaceId,
                address = place.address,
                phone = place.phone,
                longitude = place.longitude.value,
                latitude = place.latitude.value,
            )
        }
    }
}
