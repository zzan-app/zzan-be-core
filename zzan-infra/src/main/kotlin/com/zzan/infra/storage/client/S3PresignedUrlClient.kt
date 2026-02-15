package com.zzan.infra.storage.client

import com.zzan.common.type.ImagePrefix
import com.zzan.infra.storage.dto.PreSignedUrlResponse
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.*

@Component
class S3PresignedUrlClient(
    private val s3Presigner: S3Presigner,
    private val s3Properties: S3Properties
) {
    fun getPresignedUrl(type: ImagePrefix, fileName: String): PreSignedUrlResponse {
        val fileNameWithUuid = "${UUID.randomUUID()}-$fileName"
        val filePath = "${type.path}/$fileNameWithUuid"

        val objectRequest = PutObjectRequest.builder()
            .bucket(s3Properties.s3.bucket)
            .key(filePath)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(5))
            .putObjectRequest(objectRequest)
            .build()

        return PreSignedUrlResponse(
            s3Presigner.presignPutObject(presignRequest).url().toString(),
            filePath
        )
    }
}
