package com.zzan.feed.adapter.out

import com.zzan.feed.application.port.out.FeedViewCountRepository
import org.springframework.data.redis.core.ScanOptions
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
        val keys = mutableListOf<String>()

        stringRedisTemplate.execute { connection ->
            val options = ScanOptions.scanOptions()
                .match("view:*")
                .count(100)
                .build()
            connection.keyCommands().scan(options).use { cursor ->
                while (cursor.hasNext()) keys.add(String(cursor.next()))
            }
        }

        return keys.mapNotNull { key ->
            val bytes = stringRedisTemplate.execute { connection ->
                connection.stringCommands().getDel(key.toByteArray())
            } ?: return@mapNotNull null

            val count = String(bytes).toLong()
            if (count == 0L) return@mapNotNull null

            key.removePrefix("view:") to count
        }.toMap()
    }
}
