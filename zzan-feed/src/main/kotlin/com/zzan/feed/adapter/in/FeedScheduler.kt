package com.zzan.feed.adapter.`in`

import com.zzan.feed.application.port.`in`.FeedUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FeedScheduler(
    private val feedUseCase: FeedUseCase,
) {
    @Scheduled(fixedDelay = 60_000)
    fun syncViewCount() {
        feedUseCase.syncViewCounts()
    }
}
