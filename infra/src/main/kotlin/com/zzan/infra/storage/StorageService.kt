package com.zzan.infra.storage

import com.zzan.common.type.ImagePrefix
import com.zzan.infra.storage.client.S3PresignedUrlClient
import com.zzan.infra.storage.dto.PreSignedUrlResponse
import com.zzan.infra.storage.dto.PresignedUrlRequest
import org.springframework.stereotype.Service


@Service
class StorageService(
    private val presignedUrlClient: S3PresignedUrlClient
) {
    fun generatePreSignedUrl(prefix: ImagePrefix, request: PresignedUrlRequest): PreSignedUrlResponse {
        return presignedUrlClient.getPresignedUrl(prefix, request.fileName)
    }
}
