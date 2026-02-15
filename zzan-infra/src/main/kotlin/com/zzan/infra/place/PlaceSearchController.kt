package com.zzan.infra.place

import com.zzan.common.dto.CursorPageResponse
import com.zzan.common.response.ApiResponse
import com.zzan.infra.place.dto.PlaceResult
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/infra/places")
class PlaceSearchController(
    private val placeSearchService: PlaceSearchService
) {
    @GetMapping("/search")
    fun search(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") @Min(1) @Max(15) size: Int
    ): ApiResponse<CursorPageResponse<PlaceResult>> =
        ApiResponse.ok(placeSearchService.search(keyword, page, size))
}
