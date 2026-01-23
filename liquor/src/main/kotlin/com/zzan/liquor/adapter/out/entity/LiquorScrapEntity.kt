package com.zzan.liquor.adapter.out.entity

import com.github.f4b6a3.ulid.UlidCreator
import com.zzan.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "liquor_scraps",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["user_id", "liquor_id"])
    ]
)
class LiquorScrapEntity(
    id: String = UlidCreator.getUlid().toString(),

    @Column(name = "user_id", length = 26)
    val userId: String, // 스크랩한 사용자 ID

    @Column(name = "liquor_id", length = 26)
    val liquorId: String, // 스크랩한 전통주 ID
) : BaseEntity(id) {
    companion object {
        fun of(userId: String, liquorId: String) = LiquorScrapEntity(
            userId = userId,
            liquorId = liquorId,
        )
    }
}
