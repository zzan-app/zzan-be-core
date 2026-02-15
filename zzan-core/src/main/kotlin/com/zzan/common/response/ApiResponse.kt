package com.zzan.common.response

import java.time.Instant

data class ApiResponse<T>(
    val success: Boolean,
    val timestamp: Instant = Instant.now(),
    val message: String? = null,
    val data: T? = null
) {
    companion object {
        fun <T> ok(data: T): ApiResponse<T> =
            ApiResponse(success = true, data = data)

        fun <T> error(message: String): ApiResponse<T> =
            ApiResponse(success = false, message = message)
    }
}
