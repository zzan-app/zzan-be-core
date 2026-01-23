package com.zzan.place.domain

import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PlaceTest {

    private fun createPlace(
        averageScore: Double? = null,
        feedCount: Int = 0,
        totalScore: Double = 0.0
    ) = Place(
        id = "place-1",
        averageScore = averageScore,
        feedCount = feedCount,
        totalScore = totalScore,
        kakaoPlaceId = "kakao-123",
        name = "테스트 술집",
        phone = "02-1234-5678",
        address = "서울시 강남구",
        longitude = Longitude(127.0),
        latitude = Latitude(37.5)
    )

    @Nested
    @DisplayName("addScore")
    inner class AddScore {

        @Test
        @DisplayName("첫 번째 피드 점수 추가")
        fun firstScore() {
            val place = createPlace()

            val result = place.addScore(4.5)

            assertEquals(1, result.feedCount)
            assertEquals(4.5, result.totalScore)
            assertEquals(4.5, result.averageScore)
        }

        @Test
        @DisplayName("기존 점수가 있을 때 새 점수 추가")
        fun additionalScore() {
            val place = createPlace(
                averageScore = 4.0,
                feedCount = 2,
                totalScore = 8.0
            )

            val result = place.addScore(5.0)

            assertEquals(3, result.feedCount)
            assertEquals(13.0, result.totalScore)
            assertEquals(13.0 / 3, result.averageScore)
        }
    }

    @Nested
    @DisplayName("removeScore")
    inner class RemoveScore {

        @Test
        @DisplayName("점수 삭제 시 평균 재계산")
        fun removeScore() {
            val place = createPlace(
                averageScore = 4.0,
                feedCount = 2,
                totalScore = 8.0
            )

            val result = place.removeScore(5.0)

            assertEquals(1, result.feedCount)
            assertEquals(3.0, result.totalScore)
            assertEquals(3.0, result.averageScore)
        }

        @Test
        @DisplayName("마지막 피드 삭제 시 평균이 null이 된다")
        fun removeLastScore() {
            val place = createPlace(
                averageScore = 4.0,
                feedCount = 1,
                totalScore = 4.0
            )

            val result = place.removeScore(4.0)

            assertEquals(0, result.feedCount)
            assertEquals(0.0, result.totalScore)
            assertNull(result.averageScore)
        }
    }
}
