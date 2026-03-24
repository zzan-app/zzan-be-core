package com.zzan.feed.application.port.out

interface FeedViewCountRepository {
    fun increment(feedId: String)
    fun getAllAndClear(): Map<String, Long>
}
