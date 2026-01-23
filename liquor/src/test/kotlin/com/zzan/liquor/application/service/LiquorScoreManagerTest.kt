package com.zzan.liquor.application.service

import com.zzan.liquor.application.port.out.LiquorRepository
import com.zzan.liquor.domain.Liquor
import com.zzan.liquor.domain.vo.Score
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
class LiquorScoreManagerTest {

    @MockK
    private lateinit var liquorRepository: LiquorRepository

    @InjectMockKs
    private lateinit var liquorScoreManager: LiquorScoreManager

    private val liquorId = "liquor-1"

    private fun createLiquor(
        averageScore: Score? = null,
        scoreCount: Int = 0,
        totalScore: Double = 0.0
    ) = Liquor(
        id = liquorId,
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
        @DisplayName("점수 추가 시 liquor가 업데이트된다")
        fun success() {
            val liquor = createLiquor()
            every { liquorRepository.findById(liquorId) } returns liquor
            every { liquorRepository.update(any()) } just Runs

            liquorScoreManager.addScore(liquorId, 4.0)

            verify {
                liquorRepository.update(match {
                    it.scoreCount == 1 && it.totalScore == 4.0
                })
            }
        }

        @Test
        @DisplayName("존재하지 않는 liquor는 무시한다")
        fun notFound() {
            every { liquorRepository.findById(liquorId) } returns null

            liquorScoreManager.addScore(liquorId, 4.0)

            verify(exactly = 0) { liquorRepository.update(any()) }
        }
    }

    @Nested
    @DisplayName("updateScore")
    inner class UpdateScore {

        @Test
        @DisplayName("점수 수정 시 liquor가 업데이트된다")
        fun success() {
            val liquor = createLiquor(
                averageScore = Score(3.0),
                scoreCount = 2,
                totalScore = 6.0
            )
            every { liquorRepository.findById(liquorId) } returns liquor
            every { liquorRepository.update(any()) } just Runs

            liquorScoreManager.updateScore(liquorId, oldScore = 4.0, newScore = 2.0)

            verify {
                liquorRepository.update(match {
                    it.totalScore == 4.0 && it.scoreCount == 2
                })
            }
        }
    }

    @Nested
    @DisplayName("removeScore")
    inner class RemoveScore {

        @Test
        @DisplayName("점수 삭제 시 liquor가 업데이트된다")
        fun success() {
            val liquor = createLiquor(
                averageScore = Score(3.0),
                scoreCount = 2,
                totalScore = 6.0
            )
            every { liquorRepository.findById(liquorId) } returns liquor
            every { liquorRepository.update(any()) } just Runs

            liquorScoreManager.removeScore(liquorId, 4.0)

            verify {
                liquorRepository.update(match {
                    it.scoreCount == 1 && it.totalScore == 2.0
                })
            }
        }
    }
}
