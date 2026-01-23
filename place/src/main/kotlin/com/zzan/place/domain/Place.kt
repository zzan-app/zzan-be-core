package com.zzan.place.domain

import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude


data class Place(
    val id: String? = null,

    val averageScore: Double? = null,
    val feedCount: Int = 0,
    val totalScore: Double = 0.0,

    val kakaoPlaceId: String,
    val name: String?,
    val phone: String? = null,
    val address: String?,

    val longitude: Longitude,
    val latitude: Latitude,
) {
    fun addScore(score: Double): Place {
        val newTotalScore = totalScore + score
        val newFeedCount = feedCount + 1
        return copy(
            totalScore = newTotalScore,
            feedCount = newFeedCount,
            averageScore = newTotalScore / newFeedCount
        )
    }

    fun removeScore(score: Double): Place {
        val newFeedCount = feedCount - 1
        if (newFeedCount <= 0) {
            return copy(totalScore = 0.0, feedCount = 0, averageScore = null)
        }
        val newTotalScore = totalScore - score
        return copy(
            totalScore = newTotalScore,
            feedCount = newFeedCount,
            averageScore = newTotalScore / newFeedCount
        )
    }
}
