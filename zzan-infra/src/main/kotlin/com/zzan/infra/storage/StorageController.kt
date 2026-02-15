package com.zzan.infra.storage

import com.zzan.common.response.ApiResponse
import com.zzan.common.type.ImagePrefix
import com.zzan.infra.storage.dto.PreSignedUrlResponse
import com.zzan.infra.storage.dto.PresignedUrlRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/storage")
class StorageController(
    private val storageService: StorageService
) {
    @PostMapping("/{prefix}/presigned-url")
    fun getPresignedUrl(
        @PathVariable prefix: String,
        @RequestBody request: PresignedUrlRequest
    ): ApiResponse<PreSignedUrlResponse> {
        return ApiResponse.ok(
            storageService.generatePreSignedUrl(ImagePrefix(prefix), request)
        )
    }
}
