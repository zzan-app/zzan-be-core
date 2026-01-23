package com.zzan.liquor.application.port.`in`

import com.zzan.common.dto.CursorPageResponse
import com.zzan.liquor.adapter.dto.response.LiquorDetailResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse

interface LiquorUseCase {
    fun getDetail(id: String): LiquorDetailResponse
    fun search(keyword: String, page: Int, size: Int): CursorPageResponse<LiquorInfoResponse>
}
