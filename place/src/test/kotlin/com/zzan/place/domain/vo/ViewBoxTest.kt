package com.zzan.place.domain.vo

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ViewBoxTest {

    @Test
    @DisplayName("유효한 범위로 ViewBox를 생성할 수 있다")
    fun validViewBox() {
        val viewBox = ViewBox(
            minLongitude = Longitude(126.0),
            maxLongitude = Longitude(128.0),
            minLatitude = Latitude(36.0),
            maxLatitude = Latitude(38.0)
        )
        assertNotNull(viewBox)
    }

    @Test
    @DisplayName("최소 경도가 최대 경도보다 크면 예외가 발생한다")
    fun invalidLongitudeRange() {
        assertThrows<IllegalArgumentException> {
            ViewBox(
                minLongitude = Longitude(128.0),
                maxLongitude = Longitude(126.0),
                minLatitude = Latitude(36.0),
                maxLatitude = Latitude(38.0)
            )
        }
    }

    @Test
    @DisplayName("최소 위도가 최대 위도보다 크면 예외가 발생한다")
    fun invalidLatitudeRange() {
        assertThrows<IllegalArgumentException> {
            ViewBox(
                minLongitude = Longitude(126.0),
                maxLongitude = Longitude(128.0),
                minLatitude = Latitude(38.0),
                maxLatitude = Latitude(36.0)
            )
        }
    }

    @Test
    @DisplayName("같은 값의 최소/최대 경도는 허용된다")
    fun sameLongitude() {
        val viewBox = ViewBox(
            minLongitude = Longitude(127.0),
            maxLongitude = Longitude(127.0),
            minLatitude = Latitude(36.0),
            maxLatitude = Latitude(38.0)
        )
        assertNotNull(viewBox)
    }
}
