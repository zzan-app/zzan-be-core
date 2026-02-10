package com.zzan.place.application.service

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import com.zzan.place.application.port.out.PlaceRepository
import com.zzan.place.domain.Place
import com.zzan.place.domain.vo.Latitude
import com.zzan.place.domain.vo.Longitude
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceScoreManager(
    private val placeRepository: PlaceRepository,
) {
    @Transactional
    @Retryable(value = [OptimisticLockingFailureException::class], maxAttempts = 3)
    fun addScore(event: FeedCreated) {
        val kakaoPlaceId = event.kakaoPlaceId ?: return
        val score = event.score ?: return
        val place = placeRepository.findByKakaoPlaceId(kakaoPlaceId)
            ?: createPlace(event)
        placeRepository.update(place.addScore(score))
    }

    @Transactional
    @Retryable(value = [OptimisticLockingFailureException::class], maxAttempts = 3)
    fun removeScore(event: FeedDeleted) {
        val kakaoPlaceId = event.kakaoPlaceId ?: return
        val score = event.score ?: return
        val place = placeRepository.findByKakaoPlaceId(kakaoPlaceId) ?: return
        placeRepository.update(place.removeScore(score))
    }

    private fun createPlace(event: FeedCreated): Place {
        return placeRepository.save(
            Place(
                kakaoPlaceId = event.kakaoPlaceId!!,
                name = event.placeName,
                address = event.placeAddress,
                phone = event.placePhone,
                longitude = Longitude(event.longitude!!),
                latitude = Latitude(event.latitude!!)
            )
        )
    }
}
