package com.zzan.user.adapter.out

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.AuditableEntity
import com.zzan.common.type.SocialProvider
import com.zzan.user.domain.User
import com.zzan.user.domain.UserRole
import com.zzan.user.domain.vo.Birth
import com.zzan.user.domain.vo.Email
import com.zzan.user.domain.vo.Phone
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class UserEntity(
    id: String,

    @Version
    var version: Long = 0,

    var name: String? = null,

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null,

    @Column(name = "review_count")
    var reviewCount: Int = 0,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,


    @Column(name = "social_id")
    val socialId: String? = null,

    @Enumerated(EnumType.STRING)
    val provider: SocialProvider? = null,


    var email: String? = null,
    var phone: String? = null,
    var birth: LocalDate? = null,
) : AuditableEntity(id) {

    fun toDomain(): User {
        return User(
            id = id,
            socialId = socialId,
            provider = provider,
            profileImageUrl = profileImageUrl,
            name = name,
            role = role,
            email = email?.let { Email(it) },
            birth = birth?.let { Birth(it) },
            phone = phone?.let { Phone(it) },
        )
    }

    fun update(user: User) {
        this.name = user.name
        this.profileImageUrl = user.profileImageUrl
        this.role = user.role
        this.email = user.email?.value
        this.phone = user.phone?.value
        this.birth = user.birth?.value
    }

    companion object {
        fun of(user: User): UserEntity {
            return UserEntity(
                id = user.id ?: UlidCreator.getUlid().toString(),
                socialId = user.socialId,
                provider = user.provider,
                profileImageUrl = user.profileImageUrl,
                name = user.name,
                role = user.role,
            )
        }
    }
}
