package com.zzan.feed.adapter.out.event

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted
import com.zzan.feed.application.port.out.FeedEventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class FeedSpringEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) : FeedEventPublisher {
    override fun publish(event: FeedCreated) {
        eventPublisher.publishEvent(event)
    }

    override fun publish(event: FeedDeleted) {
        eventPublisher.publishEvent(event)
    }
}
