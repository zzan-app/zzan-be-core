package com.zzan.liquor.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.AuditableEntity
import com.zzan.liquor.domain.Liquor
import com.zzan.liquor.domain.vo.Score
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "liquors")
class LiquorEntity(
    id: String,

    @Version
    var version: Long = 0,

    val name: String,
    val type: String?, // 탁주, 약주, 증류주 등

    @Column(name = "average_score")
    var averageScore: Double? = null,
    @Column(name = "score_count")
    var scoreCount: Int = 0, // 평점 개수
    @Column(name = "total_score")
    var totalScore: Double = 0.0,

    @Column(columnDefinition = "TEXT")
    val description: String?,
    @Column(name = "food_pairing", columnDefinition = "TEXT")
    val foodPairing: String?,

    val volume: String?, // 술 용량 (500ml, 750ml 등)
    val content: String?, // 도수 (7%, 8%, ...)
    val awards: String?, // 수상내역
    val etc: String?, // 기타사항 (무감미료, 진함 등)

    @Column(name = "image_url")
    val imageUrl: String?, // 이미지 URL
    val brewery: String?, // 양조장 이름

) : AuditableEntity(id) {
    fun toDomain(): Liquor {
        return Liquor(
            id = id,
            name = name,
            type = type,
            averageScore = averageScore?.let { Score(it) },
            description = description,
            foodPairing = foodPairing,
            volume = volume,
            content = content,
            awards = awards,
            etc = etc,
            imageUrl = imageUrl,
            brewery = brewery,
        )
    }

    fun update(liquor: Liquor) {
        this.averageScore = liquor.averageScore?.value
        this.scoreCount = liquor.scoreCount
        this.totalScore = liquor.totalScore
    }

    companion object {
        fun of(liquor: Liquor): LiquorEntity {
            return LiquorEntity(
                id = liquor.id ?: UlidCreator.getUlid().toString(),
                name = liquor.name,
                type = liquor.type ?: "",
                averageScore = liquor.averageScore?.value,
                scoreCount = liquor.scoreCount,
                totalScore = liquor.totalScore,
                description = liquor.description,
                foodPairing = liquor.foodPairing,
                volume = liquor.volume,
                content = liquor.content,
                awards = liquor.awards,
                etc = liquor.etc,
                imageUrl = liquor.imageUrl,
                brewery = liquor.brewery,
            )
        }
    }
}
