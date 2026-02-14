package com.zzan.liquor.application.service

import com.zzan.common.dto.CursorPageRequest
import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.dto.ExistResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse
import com.zzan.liquor.application.port.`in`.LiquorScrapUseCase
import com.zzan.liquor.application.port.out.LiquorScrapRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LiquorScrapService(
    private val liquorScrapRepository: LiquorScrapRepository,
) : LiquorScrapUseCase {
    override fun getList(userId: String, pageRequest: CursorPageRequest): CursorPageResponse<LiquorInfoResponse> {
        val items = liquorScrapRepository.getList(
            userId = userId,
            cursor = pageRequest.cursor,
            pageable = PageRequest.of(0, pageRequest.size + 1)
        ).map { LiquorInfoResponse.of(it) }

        return CursorPageResponse.of(items, pageRequest.size) { it.id }
    }

    override fun getExist(userId: String, liquorId: String): ExistResponse =
        ExistResponse(liquorScrapRepository.getExist(userId, liquorId))

    @Transactional
    override fun create(userId: String, liquorId: String) {
        liquorScrapRepository.create(userId, liquorId)
    }

    @Transactional
    override fun delete(userId: String, liquorId: String) {
        liquorScrapRepository.delete(userId, liquorId)
    }
}
