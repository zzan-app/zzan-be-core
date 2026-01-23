package com.zzan.feed.adapter.out.jpa

import com.zzan.feed.adapter.out.entity.FeedLiquorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FeedLiquorJpaRepository : JpaRepository<FeedLiquorEntity, String>

