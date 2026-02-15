package com.zzan.common.type

import com.zzan.common.exception.CustomException
import org.springframework.http.HttpStatus

enum class ImagePrefix(val path: String) {
    LIQUOR("liquor-images"),
    FEED("feed-images"),
    USER_PROFILE("user-profile-images");

    companion object {
        operator fun invoke(prefix: String): ImagePrefix {
            return entries.find { it.path == prefix }
                ?: throw CustomException(HttpStatus.BAD_REQUEST, "알 수 없는 image prefix: $prefix")
        }
    }
}
