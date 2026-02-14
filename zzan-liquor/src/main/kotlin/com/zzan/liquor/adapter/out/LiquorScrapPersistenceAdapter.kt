package com.zzan.liquor.adapter.out

import com.zzan.liquor.adapter.out.entity.LiquorScrapEntity
import com.zzan.liquor.adapter.out.jpa.LiquorScrapJpaRepository
import com.zzan.liquor.application.port.out.LiquorScrapRepository
import com.zzan.liquor.domain.Liquor
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class LiquorScrapPersistenceAdapter(
    private val liquorScrapJpaRepository: LiquorScrapJpaRepository
) : LiquorScrapRepository {

    override fun getList(
        userId: String,
        cursor: String?,
        pageable: Pageable
    ): List<Liquor> =
        liquorScrapJpaRepository.findByUserId(userId, cursor, pageable)
            .map { it.toDomain() }

    override fun getExist(userId: String, liquorId: String): Boolean =
        liquorScrapJpaRepository.existsByUserIdAndLiquorId(userId, liquorId)

    override fun create(userId: String, liquorId: String) {
        liquorScrapJpaRepository.save(LiquorScrapEntity.of(userId, liquorId))
    }

    override fun delete(userId: String, liquorId: String) {
        liquorScrapJpaRepository.deleteByUserIdAndLiquorId(userId, liquorId)
    }
}
