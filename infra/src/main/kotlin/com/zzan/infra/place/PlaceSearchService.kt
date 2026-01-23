package com.zzan.infra.place

import com.zzan.common.dto.CursorPageResponse
import com.zzan.infra.place.client.KakaoMapClient
import com.zzan.infra.place.dto.PlaceResult
import org.springframework.stereotype.Service

@Service
class PlaceSearchService(
    private val kakaoMapClient: KakaoMapClient
) {
    fun search(keyword: String, page: Int, size: Int): CursorPageResponse<PlaceResult> {
        val response = kakaoMapClient.searchPlaces(keyword, page, size)
        val items = response.documents.map { PlaceResult.of(it) }

        return CursorPageResponse(
            items = items,
            nextCursor = if (response.meta.isEnd) null else (page + 1).toString(),
            hasNext = !response.meta.isEnd
        )
    }
}
