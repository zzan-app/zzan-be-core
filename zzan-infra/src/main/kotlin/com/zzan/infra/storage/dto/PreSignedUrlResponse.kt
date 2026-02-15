package com.zzan.infra.storage.dto

data class PreSignedUrlResponse(
    val url: String,
    val key: String,
)
