package com.zzan.user.domain

import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.dto.TokenUserInfo
import com.zzan.common.type.SocialProvider
import com.zzan.user.domain.vo.Birth
import com.zzan.user.domain.vo.Email
import com.zzan.user.domain.vo.Phone

data class User(
    val id: String? = null,

    val socialId: String?,
    val provider: SocialProvider?,

    val profileImageUrl: String?,
    val name: String?,
    val role: UserRole = UserRole.USER,

    val reviewCount: Int = 0,

    val birth: Birth? = null,
    val email: Email? = null,
    val phone: Phone? = null,
) {
    fun toTokenUserInfo(): TokenUserInfo {
        return TokenUserInfo(
            userId = id!!,
            role = role.name,
        )
    }

    fun toBasicUserInfo(): BaseUserInfo {
        return BaseUserInfo(
            id = id!!,
            name = name,
            profileImage = profileImageUrl,
        )
    }
}
