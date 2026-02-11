package com.zzan.user.application.port.out

import com.zzan.common.type.SocialProvider
import com.zzan.user.domain.User

interface UserRepository {
    fun save(user: User): User
    fun findById(id: String): User?
    fun findBySocialIdAndProvider(socialId: String, provider: SocialProvider): User?
    fun update(user: User)
}
