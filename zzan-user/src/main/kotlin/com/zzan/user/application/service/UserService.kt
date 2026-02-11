package com.zzan.user.application.service


import com.zzan.common.dto.BaseUserInfo
import com.zzan.common.exception.CustomException
import com.zzan.common.type.SocialProvider
import com.zzan.user.adapter.dto.`in`.SocialUserResult
import com.zzan.user.adapter.dto.`in`.UpdateUser
import com.zzan.user.adapter.dto.out.UserDetailResponse
import com.zzan.user.application.port.`in`.UserUseCase
import com.zzan.user.application.port.out.UserRepository
import com.zzan.user.domain.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) : UserUseCase {

    @Transactional
    override fun create(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    override fun createFromSocial(socialUserResult: SocialUserResult): User {
        return userRepository.save(socialUserResult.toDomain())
    }

    @Transactional(readOnly = false)
    override fun update(userId: String, request: UpdateUser) {
        val user = userRepository.findById(userId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        userRepository.update(request.toDomain(user))
    }

    override fun getUserDetail(id: String): UserDetailResponse {
        val user = userRepository.findById(id)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        return UserDetailResponse.of(user)
    }

    override fun getBasicUserInfo(id: String): BaseUserInfo {
        val user = userRepository.findById(id)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
        return user.toBasicUserInfo()
    }

    override fun findBySocialId(socialId: String, provider: SocialProvider): User? {
        return userRepository.findBySocialIdAndProvider(socialId, provider)
    }

    override fun getById(id: String): User {
        return userRepository.findById(id)
            ?: throw CustomException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
    }
}
