package com.zzan.liquor.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.AuditableEntity
import com.zzan.liquor.domain.LiquorReview
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table


@Entity
@Table(
    name = "liquor_reviews",
    uniqueConstraints = [
        jakarta.persistence.UniqueConstraint(columnNames = ["user_id", "liquor_id"])
    ]
)
class LiquorReviewEntity(
    id: String,

    @Column(name = "user_id", length = 26)
    val userId: String,
    var username: String?,
    @Column(name = "user_profile_image_url")
    var userProfileImageUrl: String?,

    @Column(name = "liquor_id", length = 26)
    val liquorId: String,
    val liquorName: String,

    var score: Double,

    @Column(columnDefinition = "TEXT")
    var text: String? = null,
) : AuditableEntity(id) {
    fun toDomain() = LiquorReview(
        id = this.id,

        userId = this.userId,
        username = this.username,
        userProfileImageUrl = this.userProfileImageUrl,

        liquorId = this.liquorId,
        liquorName = this.liquorName,

        score = this.score,
        text = this.text,
        createdAt = this.createdAt,
    )

    fun update(review: LiquorReview) {
        this.username = review.username
        this.userProfileImageUrl = review.userProfileImageUrl
        this.score = review.score
        this.text = review.text
    }

    companion object {
        fun of(review: LiquorReview): LiquorReviewEntity {
            return LiquorReviewEntity(
                id = review.id ?: UlidCreator.getUlid().toString(),
                userId = review.userId,
                username = review.username,
                userProfileImageUrl = review.userProfileImageUrl,

                liquorId = review.liquorId,
                liquorName = review.liquorName,

                score = review.score,
                text = review.text,
            )
        }
    }
}
