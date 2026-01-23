package com.zzan.user.adapter.out

import com.zzan.common.type.SocialProvider
import com.zzan.user.application.port.out.UserRepository
import com.zzan.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {
    override fun save(user: User): User =
        userJpaRepository.save(UserEntity.of(user)).toDomain()

    override fun findById(id: String): User? =
        userJpaRepository.findByIdOrNull(id)?.toDomain()

    override fun findBySocialIdAndProvider(socialId: String, provider: SocialProvider): User? =
        userJpaRepository.findBySocialIdAndProvider(socialId, provider)?.toDomain()

    override fun update(user: User) {
        userJpaRepository.findByIdOrNull(user.id!!)?.update(user)
    }
}
