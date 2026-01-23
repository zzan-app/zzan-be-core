package com.zzan.place.domain.vo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LongitudeTest {

    @ParameterizedTest
    @ValueSource(doubles = [-180.0, -127.0, 0.0, 127.0, 180.0])
    @DisplayName("유효한 경도(-180~180)로 생성할 수 있다")
    fun validLongitude(value: Double) {
        val longitude = Longitude(value)
        assertEquals(value, longitude.value)
    }

    @ParameterizedTest
    @ValueSource(doubles = [-180.1, -200.0, 180.1, 360.0])
    @DisplayName("유효하지 않은 경도로 생성 시 예외가 발생한다")
    fun invalidLongitude(value: Double) {
        assertThrows<IllegalArgumentException> {
            Longitude(value)
        }
    }
}
