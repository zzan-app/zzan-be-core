package com.zzan.liquor.application.service

import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.exception.CustomException
import com.zzan.liquor.adapter.dto.request.CreateLiquorReview
import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import com.zzan.liquor.adapter.dto.response.LiquorReviewResponse
import com.zzan.liquor.application.port.out.LiquorRepository
import com.zzan.liquor.application.port.out.LiquorReviewRepository
import com.zzan.liquor.domain.Liquor
import com.zzan.liquor.domain.LiquorReview
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class LiquorReviewServiceTest {

    @MockK
    private lateinit var liquorReviewRepository: LiquorReviewRepository

    @MockK
    private lateinit var liquorRepository: LiquorRepository

    @MockK
    private lateinit var liquorScoreManager: LiquorScoreManager

    @InjectMockKs
    private lateinit var liquorReviewService: LiquorReviewService

    private val userId = "user-1"
    private val liquorId = "liquor-1"

    private fun createUserInfo() = BaseUserInfo(
        id = userId,
        name = "테스터",
        profileImage = null
    )

    private fun createLiquor() = Liquor(
        id = liquorId,
        name = "테스트 막걸리",
        type = "탁주",
        imageUrl = null,
        description = null,
        foodPairing = null,
        volume = "750ml",
        content = "6%",
        awards = null,
        etc = null,
        brewery = "테스트 양조장"
    )

    private fun createReview(score: Double = 4.0) = LiquorReview(
        id = "review-1",
        userId = userId,
        username = "테스터",
        userProfileImageUrl = null,
        liquorId = liquorId,
        liquorName = "테스트 막걸리",
        score = score,
        text = "맛있어요"
    )

    @Nested
    @DisplayName("find")
    inner class Find {

        @Test
        @DisplayName("리뷰가 있으면 반환한다")
        fun found() {
            val review = createReview()
            every { liquorReviewRepository.find(userId, liquorId) } returns review

            val result = liquorReviewService.find(userId, liquorId)

            val expected = LiquorReviewResponse.of(review)
            assertEquals(expected, result)
        }

        @Test
        @DisplayName("리뷰가 없으면 null 반환")
        fun notFound() {
            every { liquorReviewRepository.find(userId, liquorId) } returns null

            val result = liquorReviewService.find(userId, liquorId)

            assertNull(result)
        }
    }

    @Nested
    @DisplayName("create")
    inner class Create {

        @Test
        @DisplayName("리뷰 생성 성공")
        fun success() {
            val userInfo = createUserInfo()
            val liquor = createLiquor()
            val request = CreateLiquorReview(score = 4.5, text = "맛있어요")

            every { liquorRepository.findById(liquorId) } returns liquor
            every { liquorReviewRepository.save(any()) } returns createReview(4.5)
            every { liquorScoreManager.addScore(liquorId, 4.5) } just Runs

            liquorReviewService.create(userInfo, liquorId, request)

            verify { liquorReviewRepository.save(any()) }
            verify { liquorScoreManager.addScore(liquorId, 4.5) }
        }

        @Test
        @DisplayName("존재하지 않는 주류에 리뷰 생성 시 예외 발생")
        fun liquorNotFound() {
            val userInfo = createUserInfo()
            val request = CreateLiquorReview(score = 4.5, text = "맛있어요")

            every { liquorRepository.findById(liquorId) } returns null

            assertThrows<CustomException> {
                liquorReviewService.create(userInfo, liquorId, request)
            }
            verify(exactly = 0) { liquorReviewRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("update")
    inner class Update {

        @Test
        @DisplayName("리뷰 수정 성공")
        fun success() {
            val review = createReview(score = 3.0)
            val request = UpdateLiquorReview(score = 5.0, text = "다시 먹어보니 맛있네요")

            every { liquorReviewRepository.find(userId, liquorId) } returns review
            every { liquorReviewRepository.update(any()) } just Runs
            every { liquorScoreManager.updateScore(liquorId, 3.0, 5.0) } just Runs

            liquorReviewService.update(userId, liquorId, request)

            verify { liquorReviewRepository.update(any()) }
            verify { liquorScoreManager.updateScore(liquorId, 3.0, 5.0) }
        }

        @Test
        @DisplayName("존재하지 않는 리뷰 수정 시 예외 발생")
        fun notFound() {
            val request = UpdateLiquorReview(score = 5.0, text = "수정")

            every { liquorReviewRepository.find(userId, liquorId) } returns null

            assertThrows<CustomException> {
                liquorReviewService.update(userId, liquorId, request)
            }
        }
    }

    @Nested
    @DisplayName("delete")
    inner class Delete {

        @Test
        @DisplayName("리뷰 삭제 성공")
        fun success() {
            val review = createReview(score = 4.0)

            every { liquorReviewRepository.find(userId, liquorId) } returns review
            every { liquorReviewRepository.delete(userId, liquorId) } just Runs
            every { liquorScoreManager.removeScore(liquorId, 4.0) } just Runs

            liquorReviewService.delete(userId, liquorId)

            verify { liquorReviewRepository.delete(userId, liquorId) }
            verify { liquorScoreManager.removeScore(liquorId, 4.0) }
        }

        @Test
        @DisplayName("존재하지 않는 리뷰 삭제 시 예외 발생")
        fun notFound() {
            every { liquorReviewRepository.find(userId, liquorId) } returns null

            assertThrows<CustomException> {
                liquorReviewService.delete(userId, liquorId)
            }
        }
    }
}
