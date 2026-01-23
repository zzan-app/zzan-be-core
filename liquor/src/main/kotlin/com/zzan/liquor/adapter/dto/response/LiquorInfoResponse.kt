package com.zzan.liquor.adapter.dto.response

import com.zzan.common.annotation.ImageUrl
import com.zzan.liquor.domain.Liquor

data class LiquorInfoResponse(
    val id: String,
    val liquorName: String,
    val liquorScore: Double?,
    @ImageUrl
    val liquorImageUrl: String?,
    val liquorType: String?,
) {
    companion object {
        fun of(liquor: Liquor): LiquorInfoResponse {
            return LiquorInfoResponse(
                id = liquor.id!!,
                liquorName = liquor.name,
                liquorScore = liquor.averageScore?.value,
                liquorImageUrl = liquor.imageUrl,
                liquorType = liquor.type,
            )
        }
    }
}
