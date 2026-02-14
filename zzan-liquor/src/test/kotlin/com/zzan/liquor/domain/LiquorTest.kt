package com.zzan.liquor.domain

import com.zzan.liquor.domain.vo.Score
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LiquorTest {

    private fun createLiquor(
        averageScore: Score? = null,
        scoreCount: Int = 0,
        totalScore: Double = 0.0
    ) = Liquor(
        id = "liquor-1",
        name = "테스트 막걸리",
        type = "탁주",
        imageUrl = null,
        averageScore = averageScore,
        scoreCount = scoreCount,
        totalScore = totalScore,
        description = null,
        foodPairing = null,
        volume = "750ml",
        content = "6%",
        awards = null,
        etc = null,
        brewery = "테스트 양조장"
    )

    @Nested
    @DisplayName("addScore")
    inner class AddScore {

        @Test
        @DisplayName("첫 번째 점수 추가 시 평균 점수가 해당 점수가 된다")
        fun firstScore() {
            val liquor = createLiquor()

            val result = liquor.addScore(4.0)

            assertEquals(1, result.scoreCount)
            assertEquals(4.0, result.totalScore)
            assertEquals(4.0, result.averageScore?.value)
        }

        @Test
        @DisplayName("기존 점수가 있을 때 새 점수 추가 시 평균이 재계산된다")
        fun additionalScore() {
            val liquor = createLiquor(
                averageScore = Score(4.0),
                scoreCount = 1,
                totalScore = 4.0
            )

            val result = liquor.addScore(2.0)

            assertEquals(2, result.scoreCount)
            assertEquals(6.0, result.totalScore)
            assertEquals(3.0, result.averageScore?.value)
        }
    }

    @Nested
    @DisplayName("updateScore")
    inner class UpdateScore {

        @Test
        @DisplayName("점수 수정 시 평균이 재계산된다")
        fun updateScore() {
            val liquor = createLiquor(
                averageScore = Score(3.0),
                scoreCount = 2,
                totalScore = 6.0
            )

            val result = liquor.updateScore(oldScore = 4.0, newScore = 2.0)

            assertEquals(2, result.scoreCount)
            assertEquals(4.0, result.totalScore)
            assertEquals(2.0, result.averageScore?.value)
        }
    }

    @Nested
    @DisplayName("removeScore")
    inner class RemoveScore {

        @Test
        @DisplayName("점수 삭제 시 평균이 재계산된다")
        fun removeScore() {
            val liquor = createLiquor(
                averageScore = Score(3.0),
                scoreCount = 2,
                totalScore = 6.0
            )

            val result = liquor.removeScore(4.0)

            assertEquals(1, result.scoreCount)
            assertEquals(2.0, result.totalScore)
            assertEquals(2.0, result.averageScore?.value)
        }

        @Test
        @DisplayName("마지막 점수 삭제 시 평균이 null이 된다")
        fun removeLastScore() {
            val liquor = createLiquor(
                averageScore = Score(4.0),
                scoreCount = 1,
                totalScore = 4.0
            )

            val result = liquor.removeScore(4.0)

            assertEquals(0, result.scoreCount)
            assertEquals(0.0, result.totalScore)
            assertNull(result.averageScore)
        }
    }
}
