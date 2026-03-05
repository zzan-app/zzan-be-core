package com.zzan.user.application.port.`in`

import com.zzan.core.dto.BaseUserInfo
import com.zzan.core.type.SocialProvider
import com.zzan.user.adapter.dto.`in`.SocialUserResult
import com.zzan.user.adapter.dto.`in`.UpdateUser
import com.zzan.user.adapter.dto.out.UserDetailResponse
import com.zzan.user.domain.User

interface UserUseCase {
    fun create(user: User): User
    fun createFromSocial(socialUserResult: SocialUserResult): User
    fun update(userId: String, request: UpdateUser)
    fun getById(id: String): User
    fun getUserDetail(id: String): UserDetailResponse
    fun getBasicUserInfo(id: String): BaseUserInfo
    fun findBySocialId(socialId: String, provider: SocialProvider): User?
}
