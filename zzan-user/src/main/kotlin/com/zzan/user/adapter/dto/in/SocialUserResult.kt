package com.zzan.user.adapter.dto.`in`

import com.zzan.common.type.SocialProvider
import com.zzan.user.domain.User

data class SocialUserResult(
    val socialId: String,
    val name: String?,
    val profileImageUrl: String?,
    val provider: SocialProvider
) {
    fun toDomain(): User {
        return User(
            socialId = socialId,
            name = name,
            profileImageUrl = profileImageUrl,
            provider = provider
        )
    }
}
