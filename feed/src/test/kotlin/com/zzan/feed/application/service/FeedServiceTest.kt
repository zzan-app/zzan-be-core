package com.zzan.feed.application.service

import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import com.zzan.common.exception.CustomException
import com.zzan.feed.adapter.dto.request.CreateFeed
import com.zzan.feed.application.port.out.FeedEventPublisher
import com.zzan.feed.application.port.out.FeedLiquorRepository
import com.zzan.feed.application.port.out.FeedRepository
import com.zzan.feed.domain.Feed
import com.zzan.feed.domain.vo.Score
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FeedServiceTest {

    @MockK
    private lateinit var feedRepository: FeedRepository

    @MockK
    private lateinit var feedLiquorRepository: FeedLiquorRepository

    @MockK
    private lateinit var feedEventPublisher: FeedEventPublisher

    @InjectMockKs
    private lateinit var feedService: FeedService

    private val userId = "user-1"
    private val feedId = "feed-1"
    private val baseUrl = "https://cdn.example.com"

    private fun createFeed(id: String? = feedId, userId: String = this.userId) = Feed(
        id = id,
        userId = userId,
        userName = "테스터",
        userProfileImage = null,
        images = emptyList(),
        score = Score(4.0),
        text = "맛있어요",
        kakaoPlaceId = "kakao-123",
        placeName = "테스트 술집",
        placeAddress = "서울시 강남구"
    )

    private fun createRequest() = CreateFeed(
        score = 4.0,
        text = "맛있어요",
        images = emptyList(),
        kakaoPlaceId = "kakao-123",
        placeName = "테스트 술집",
        placePhone = "02-1234-5678",
        placeAddress = "서울시 강남구",
        longitude = 127.0,
        latitude = 37.5
    )

    @Nested
    @DisplayName("getDetail")
    inner class GetDetail {

        @Test
        @DisplayName("존재하는 피드 조회 성공")
        fun success() {
            val feed = createFeed()
            every { feedRepository.findById(feedId) } returns feed

            val result = feedService.getDetail(feedId)

            assertEquals(feedId, result.id)
            verify { feedRepository.findById(feedId) }
        }

        @Test
        @DisplayName("존재하지 않는 피드 조회 시 예외 발생")
        fun notFound() {
            every { feedRepository.findById(feedId) } returns null

            assertThrows<CustomException> {
                feedService.getDetail(feedId)
            }
        }
    }

    @Nested
    @DisplayName("create")
    inner class Create {

        @Test
        @DisplayName("피드 생성 성공")
        fun success() {
            val userInfo = BaseUserInfo(id = userId, name = "테스터", profileImage = null)
            val request = createRequest()
            val savedFeed = createFeed(id = feedId)

            every { feedRepository.save(any()) } returns savedFeed
            every { feedLiquorRepository.saveAll(feedId, any()) } just Runs
            every { feedEventPublisher.publish(any<FeedCreated>()) } just Runs

            val result = feedService.create(userInfo, request)

            assertEquals(feedId, result.id)
            verify { feedRepository.save(any()) }
            verify { feedLiquorRepository.saveAll(feedId, any()) }
            verify { feedEventPublisher.publish(any<FeedCreated>()) }
        }
    }

    @Nested
    @DisplayName("delete")
    inner class Delete {

        @Test
        @DisplayName("본인 피드 삭제 성공")
        fun success() {
            val feed = createFeed(userId = userId)
            every { feedRepository.findById(feedId) } returns feed
            every { feedRepository.delete(feedId) } just Runs
            every { feedEventPublisher.publish(any<FeedDeleted>()) } just Runs

            feedService.delete(userId, feedId)

            verify { feedRepository.delete(feedId) }
            verify { feedEventPublisher.publish(any<FeedDeleted>()) }
        }

        @Test
        @DisplayName("다른 사용자의 피드 삭제 시 예외 발생")
        fun forbidden() {
            val feed = createFeed(userId = "other-user")
            every { feedRepository.findById(feedId) } returns feed

            assertThrows<CustomException> {
                feedService.delete(userId, feedId)
            }
            verify(exactly = 0) { feedRepository.delete(any()) }
            verify(exactly = 0) { feedEventPublisher.publish(any<FeedDeleted>()) }
        }

        @Test
        @DisplayName("존재하지 않는 피드 삭제 시 예외 발생")
        fun notFound() {
            every { feedRepository.findById(feedId) } returns null

            assertThrows<CustomException> {
                feedService.delete(userId, feedId)
            }
        }
    }
}
