package com.zzan.common.exception

import org.springframework.http.HttpStatus

class CustomException(
    val status: HttpStatus,
    override val message: String? = status.reasonPhrase
) : RuntimeException(message)
