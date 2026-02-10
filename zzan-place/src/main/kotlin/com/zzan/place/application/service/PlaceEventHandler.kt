package com.zzan.place.application.service

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PlaceEventHandler(
    private val placeScoreManager: PlaceScoreManager,
) {
    @Async
    @TransactionalEventListener
    fun handle(event: FeedCreated) {
        placeScoreManager.addScore(event)
    }

    @Async
    @TransactionalEventListener
    fun handle(event: FeedDeleted) {
        placeScoreManager.removeScore(event)
    }
}
