package com.zzan.feed.adapter.out

import com.zzan.feed.application.port.out.FeedViewCountRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class FeedViewCountAdapter(
    private val stringRedisTemplate: StringRedisTemplate,
) : FeedViewCountRepository {

    override fun increment(feedId: String) {
        stringRedisTemplate.opsForValue().increment("view:$feedId")
    }

    override fun getAllAndClear(): Map<String, Long> {
        val keys = stringRedisTemplate.keys("view:*") ?: return emptyMap()
        return keys.mapNotNull { key ->
            val count = stringRedisTemplate.opsForValue().get(key)?.toLong() ?: return@mapNotNull null
            stringRedisTemplate.delete(key)
            key.removePrefix("view:") to count
        }.toMap()
    }
}
