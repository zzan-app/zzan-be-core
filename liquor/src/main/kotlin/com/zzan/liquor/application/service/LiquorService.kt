package com.zzan.liquor.application.service

import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.exception.CustomException
import com.zzan.liquor.adapter.dto.response.LiquorDetailResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse
import com.zzan.liquor.application.port.`in`.LiquorUseCase
import com.zzan.liquor.application.port.out.LiquorRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LiquorService(
    private val liquorRepository: LiquorRepository,
) : LiquorUseCase {

    @Cacheable("liquor", key = "#id")
    override fun getDetail(id: String): LiquorDetailResponse {
        val liquor = liquorRepository.findById(id)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 주류입니다.")
        return LiquorDetailResponse.of(liquor)
    }

    override fun search(keyword: String, page: Int, size: Int): CursorPageResponse<LiquorInfoResponse> {
        val request = PageRequest.of((page - 1).coerceAtLeast(0), size)
        val slice = liquorRepository.search(keyword, request)

        val items = slice.content.map { LiquorInfoResponse.of(it) }

        return CursorPageResponse(
            items = items,
            hasNext = slice.hasNext(),
            nextCursor = if (slice.hasNext()) (page + 1).toString() else null
        )
    }
}
