package com.zzan.feed.domain.vo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RelativeCoordinateTest {

    @ParameterizedTest
    @ValueSource(doubles = [0.0, 0.25, 0.5, 0.75, 1.0])
    @DisplayName("유효한 상대 좌표(0.0~1.0)로 생성할 수 있다")
    fun validCoordinate(value: Double) {
        val coord = RelativeCoordinate(value)
        assertEquals(value, coord.value)
    }

    @ParameterizedTest
    @ValueSource(doubles = [-0.1, -1.0, 1.1, 2.0])
    @DisplayName("유효하지 않은 상대 좌표로 생성 시 예외가 발생한다")
    fun invalidCoordinate(value: Double) {
        assertThrows<IllegalArgumentException> {
            RelativeCoordinate(value)
        }
    }
}
