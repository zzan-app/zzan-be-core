package com.zzan.user.adapter.out

import com.zzan.core.type.SocialProvider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String> {
    fun findBySocialIdAndProvider(socialId: String, provider: SocialProvider): UserEntity?
}
