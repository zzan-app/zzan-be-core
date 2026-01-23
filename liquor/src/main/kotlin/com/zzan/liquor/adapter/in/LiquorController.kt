package com.zzan.liquor.adapter.`in`

import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.response.ApiResponse
import com.zzan.liquor.adapter.dto.response.LiquorDetailResponse
import com.zzan.liquor.adapter.dto.response.LiquorInfoResponse
import com.zzan.liquor.application.port.`in`.LiquorUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/liquors")
class LiquorController(
    private val liquorUseCase: LiquorUseCase,
) {
    @GetMapping("/{liquorId}")
    fun getDetail(@PathVariable liquorId: String): ApiResponse<LiquorDetailResponse> =
        ApiResponse.ok(liquorUseCase.getDetail(liquorId))

    @GetMapping("/search")
    fun search(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<CursorPageResponse<LiquorInfoResponse>> =
        ApiResponse.ok(liquorUseCase.search(keyword, page, size))
}
