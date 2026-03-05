package com.zzan.liquor.application.port.`in`

import com.zzan.core.dto.CursorPageRequest
import com.zzan.core.dto.CursorPageResponse
import com.zzan.core.dto.ExistResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse

interface LiquorScrapUseCase {
    fun getList(userId: String, pageRequest: CursorPageRequest): CursorPageResponse<LiquorInfoResponse>
    fun getExist(userId: String, liquorId: String): ExistResponse
    fun create(userId: String, liquorId: String)
    fun delete(userId: String, liquorId: String)
}
