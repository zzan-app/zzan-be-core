package com.zzan.user.adapter.dto.out

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
)
