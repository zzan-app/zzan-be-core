package com.zzan.place.domain.vo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LatitudeTest {

    @ParameterizedTest
    @ValueSource(doubles = [-90.0, -45.0, 0.0, 37.5, 90.0])
    @DisplayName("유효한 위도(-90~90)로 생성할 수 있다")
    fun validLatitude(value: Double) {
        val latitude = Latitude(value)
        assertEquals(value, latitude.value)
    }

    @ParameterizedTest
    @ValueSource(doubles = [-90.1, -100.0, 90.1, 180.0])
    @DisplayName("유효하지 않은 위도로 생성 시 예외가 발생한다")
    fun invalidLatitude(value: Double) {
        assertThrows<IllegalArgumentException> {
            Latitude(value)
        }
    }
}
