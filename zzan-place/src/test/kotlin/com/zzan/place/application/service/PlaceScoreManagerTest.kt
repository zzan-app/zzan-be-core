package com.zzan.place.application.service

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import com.zzan.place.application.port.out.PlaceRepository
import com.zzan.place.domain.Place
import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PlaceScoreManagerTest {

    @MockK
    private lateinit var placeRepository: PlaceRepository

    @InjectMockKs
    private lateinit var placeScoreManager: PlaceScoreManager

    private val kakaoPlaceId = "kakao-123"

    private fun createPlace(
        averageScore: Double? = null,
        feedCount: Int = 0,
        totalScore: Double = 0.0
    ) = Place(
        id = "place-1",
        averageScore = averageScore,
        feedCount = feedCount,
        totalScore = totalScore,
        kakaoPlaceId = kakaoPlaceId,
        name = "테스트 술집",
        phone = "02-1234-5678",
        address = "서울시 강남구",
        longitude = Longitude(127.0),
        latitude = Latitude(37.5)
    )

    private fun createFeedCreatedEvent(
        kakaoPlaceId: String? = this.kakaoPlaceId,
        score: Double = 4.0
    ) = FeedCreated(
        feedId = "feed-1",
        score = score,
        userId = "user-1",
        kakaoPlaceId = kakaoPlaceId,
        placeName = "테스트 술집",
        placeAddress = "서울시 강남구",
        placePhone = "02-1234-5678",
        longitude = 127.0,
        latitude = 37.5
    )

    private fun createFeedDeletedEvent(
        kakaoPlaceId: String? = this.kakaoPlaceId,
        score: Double = 4.0
    ) = FeedDeleted(
        feedId = "feed-1",
        score = score,
        userId = "user-1",
        kakaoPlaceId = kakaoPlaceId
    )

    @Nested
    @DisplayName("addScore")
    inner class AddScore {

        @Test
        @DisplayName("기존 장소에 점수 추가")
        fun existingPlace() {
            val place = createPlace(averageScore = 4.0, feedCount = 1, totalScore = 4.0)
            val event = createFeedCreatedEvent(score = 5.0)

            every { placeRepository.findByKakaoPlaceId(kakaoPlaceId) } returns place
            every { placeRepository.update(any()) } just Runs

            placeScoreManager.addScore(event)

            verify {
                placeRepository.update(match {
                    it.feedCount == 2 && it.totalScore == 9.0
                })
            }
        }

        @Test
        @DisplayName("새 장소 생성 후 점수 추가")
        fun newPlace() {
            val event = createFeedCreatedEvent(score = 4.0)

            every { placeRepository.findByKakaoPlaceId(kakaoPlaceId) } returns null
            every { placeRepository.save(any()) } answers { firstArg() }
            every { placeRepository.update(any()) } just Runs

            placeScoreManager.addScore(event)

            verify(exactly = 1) { placeRepository.save(any()) }
            verify(exactly = 1) { placeRepository.update(any()) }
        }

        @Test
        @DisplayName("kakaoPlaceId가 null이면 무시")
        fun nullKakaoPlaceId() {
            val event = createFeedCreatedEvent(kakaoPlaceId = null)

            placeScoreManager.addScore(event)

            verify(exactly = 0) { placeRepository.findByKakaoPlaceId(any()) }
            verify(exactly = 0) { placeRepository.save(any()) }
            verify(exactly = 0) { placeRepository.update(any()) }
        }
    }

    @Nested
    @DisplayName("removeScore")
    inner class RemoveScore {

        @Test
        @DisplayName("점수 삭제 성공")
        fun success() {
            val place = createPlace(averageScore = 4.0, feedCount = 2, totalScore = 8.0)
            val event = createFeedDeletedEvent(score = 5.0)

            every { placeRepository.findByKakaoPlaceId(kakaoPlaceId) } returns place
            every { placeRepository.update(any()) } just Runs

            placeScoreManager.removeScore(event)

            verify {
                placeRepository.update(match {
                    it.feedCount == 1 && it.totalScore == 3.0
                })
            }
        }

        @Test
        @DisplayName("존재하지 않는 장소는 무시")
        fun notFound() {
            val event = createFeedDeletedEvent()

            every { placeRepository.findByKakaoPlaceId(kakaoPlaceId) } returns null

            placeScoreManager.removeScore(event)

            verify(exactly = 0) { placeRepository.update(any()) }
        }

        @Test
        @DisplayName("kakaoPlaceId가 null이면 무시")
        fun nullKakaoPlaceId() {
            val event = createFeedDeletedEvent(kakaoPlaceId = null)

            placeScoreManager.removeScore(event)

            verify(exactly = 0) { placeRepository.findByKakaoPlaceId(any()) }
            verify(exactly = 0) { placeRepository.update(any()) }
        }
    }
}
