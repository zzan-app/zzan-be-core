package com.zzan.liquor.domain

import com.zzan.liquor.domain.vo.Score

data class Liquor(
    val id: String? = null,
    val name: String,
    val type: String?,
    val imageUrl: String?,

    val averageScore: Score? = null,
    val scoreCount: Int = 0,
    val totalScore: Double = 0.0,

    val description: String?,
    val foodPairing: String?,

    val volume: String?,
    val content: String?,
    val awards: String?,
    val etc: String?,
    val brewery: String?,
) {
    fun addScore(score: Double): Liquor {
        val newTotalScore = totalScore + score
        val newScoreCount = scoreCount + 1
        return copy(
            totalScore = newTotalScore,
            scoreCount = newScoreCount,
            averageScore = Score(newTotalScore / newScoreCount),
        )
    }

    fun updateScore(oldScore: Double, newScore: Double): Liquor {
        val newTotalScore = totalScore - oldScore + newScore
        return copy(
            totalScore = newTotalScore,
            averageScore = Score(newTotalScore / scoreCount),
        )
    }

    fun removeScore(score: Double): Liquor {
        val newScoreCount = scoreCount - 1
        val newTotalScore = totalScore - score
        return copy(
            totalScore = newTotalScore,
            scoreCount = newScoreCount,
            averageScore = if (newScoreCount > 0) Score(newTotalScore / newScoreCount) else null,
        )
    }
}
