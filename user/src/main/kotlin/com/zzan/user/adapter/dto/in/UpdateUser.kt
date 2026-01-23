package com.zzan.user.adapter.dto.`in`

import com.zzan.user.domain.User
import com.zzan.user.domain.vo.Birth
import com.zzan.user.domain.vo.Email
import com.zzan.user.domain.vo.Phone
import java.time.LocalDate

data class UpdateUser(
    val name: String?,
    val profileImageUrl: String?,
    val birth: LocalDate?,
    val email: String?,
    val phone: String?,
) {
    fun toDomain(user: User): User = user.copy(
        name = name ?: user.name,
        profileImageUrl = profileImageUrl ?: user.profileImageUrl,
        birth = birth?.let { Birth(it) } ?: user.birth,
        email = email?.let { Email(it) } ?: user.email,
        phone = phone?.let { Phone(it) } ?: user.phone,
    )
}
