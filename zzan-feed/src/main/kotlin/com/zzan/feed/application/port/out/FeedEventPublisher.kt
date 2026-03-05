package com.zzan.feed.application.port.out

import com.zzan.core.event.feed.FeedCreated
import com.zzan.core.event.feed.FeedDeleted

interface FeedEventPublisher {
    fun publish(event: FeedCreated)
    fun publish(event: FeedDeleted)
}
