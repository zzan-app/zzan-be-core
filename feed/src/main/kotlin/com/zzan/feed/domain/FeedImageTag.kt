package com.zzan.feed.domain

import com.zzan.feed.domain.vo.RelativeCoordinate

data class FeedImageTag(
    val id: String? = null,
    val liquorId: String,
    val liquorName: String,
    val x: RelativeCoordinate,
    val y: RelativeCoordinate
) {
    companion object {
        fun of(
            liquorId: String,
            liquorName: String,
            x: Double,
            y: Double,
        ) = FeedImageTag(
            liquorId = liquorId,
            liquorName = liquorName,
            x = RelativeCoordinate(x),
            y = RelativeCoordinate(y),
        )
    }
}
