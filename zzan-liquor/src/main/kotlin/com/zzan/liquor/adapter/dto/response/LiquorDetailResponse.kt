package com.zzan.liquor.adapter.dto.response

import com.zzan.common.annotation.ImageUrl
import com.zzan.liquor.domain.Liquor

data class LiquorDetailResponse(
    val id: String,
    val name: String,
    val type: String?,
    @ImageUrl
    val imageUrl: String?,

    val score: Double?,

    val description: String?,
    val foodPairing: String?,

    val volume: String?,
    val content: String?,
    val awards: String?,
    val etc: String?,
    val brewery: String?,
) {
    companion object {
        fun of(liquor: Liquor): LiquorDetailResponse {
            return LiquorDetailResponse(
                id = liquor.id!!,
                name = liquor.name,
                type = liquor.type,
                imageUrl = liquor.imageUrl,
                score = liquor.averageScore?.value,
                description = liquor.description,
                foodPairing = liquor.foodPairing,
                volume = liquor.volume,
                content = liquor.content,
                awards = liquor.awards,
                etc = liquor.etc,
                brewery = liquor.brewery,
            )
        }
    }
}
