package com.zzan.liquor.domain

import com.zzan.liquor.adapter.dto.request.UpdateLiquorReview
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant

class LiquorReviewTest {

    private fun createReview(
        score: Double = 4.0,
        text: String? = "맛있어요"
    ) = LiquorReview(
        id = "review-1",
        userId = "user-1",
        username = "테스터",
        userProfileImageUrl = null,
        liquorId = "liquor-1",
        liquorName = "테스트 막걸리",
        score = score,
        text = text,
        createdAt = Instant.now()
    )

    @Test
    @DisplayName("리뷰 점수와 텍스트를 수정할 수 있다")
    fun updateReview() {
        val review = createReview(score = 3.0, text = "그저 그래요")
        val request = UpdateLiquorReview(score = 5.0, text = "다시 먹어보니 맛있네요")

        val updated = review.update(request)

        assertEquals(5.0, updated.score)
        assertEquals("다시 먹어보니 맛있네요", updated.text)
        // 나머지 필드는 유지
        assertEquals(review.id, updated.id)
        assertEquals(review.userId, updated.userId)
        assertEquals(review.liquorId, updated.liquorId)
    }

    @Test
    @DisplayName("텍스트 없이 점수만 수정할 수 있다")
    fun updateScoreOnly() {
        val review = createReview(score = 3.0, text = "그저 그래요")
        val request = UpdateLiquorReview(score = 4.5, text = null)

        val updated = review.update(request)

        assertEquals(4.5, updated.score)
        assertNull(updated.text)
    }

    private fun assertNull(value: Any?) {
        assertEquals(null, value)
    }
}
