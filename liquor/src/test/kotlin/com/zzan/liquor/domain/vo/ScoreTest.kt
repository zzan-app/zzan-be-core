package com.zzan.liquor.domain.vo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ScoreTest {

    @ParameterizedTest
    @ValueSource(doubles = [0.0, 1.0, 2.5, 4.0, 5.0])
    @DisplayName("유효한 점수(0.0~5.0)로 생성할 수 있다")
    fun validScore(value: Double) {
        val score = Score(value)
        assertEquals(value, score.value)
    }

    @ParameterizedTest
    @ValueSource(doubles = [-0.1, -1.0, 5.1, 10.0])
    @DisplayName("유효하지 않은 점수로 생성 시 예외가 발생한다")
    fun invalidScore(value: Double) {
        assertThrows<IllegalArgumentException> {
            Score(value)
        }
    }
}
