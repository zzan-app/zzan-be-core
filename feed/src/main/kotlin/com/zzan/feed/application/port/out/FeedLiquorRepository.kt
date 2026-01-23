package com.zzan.feed.application.port.out

interface FeedLiquorRepository {
    fun saveAll(feedId: String, liquorIds: List<String>)
}
