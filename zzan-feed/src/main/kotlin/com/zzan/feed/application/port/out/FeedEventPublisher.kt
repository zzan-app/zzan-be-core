package com.zzan.feed.application.port.out

import com.zzan.common.event.feed.FeedCreated
import com.zzan.common.event.feed.FeedDeleted

interface FeedEventPublisher {
    fun publish(event: FeedCreated)
    fun publish(event: FeedDeleted)
}
