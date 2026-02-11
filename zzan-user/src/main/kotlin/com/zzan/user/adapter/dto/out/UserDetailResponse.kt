package com.zzan.user.adapter.dto.out

import com.zzan.common.annotation.ImageUrl
import com.zzan.user.domain.User
import com.zzan.user.domain.UserRole
import java.time.LocalDate

data class UserDetailResponse(
    val id: String? = null,

    @ImageUrl
    val profileImageUrl: String?,
    val name: String?,
    val role: UserRole,

    val birth: LocalDate? = null,
    val email: String? = null,
    val phone: String? = null,
) {
    companion object {
        fun of(user: User): UserDetailResponse {
            return UserDetailResponse(
                id = user.id,
                profileImageUrl = user.profileImageUrl,
                name = user.name,
                role = user.role,
                birth = user.birth?.value,
                email = user.email?.value,
                phone = user.phone?.value,
            )
        }
    }
}
