package com.zzan.liquor.adapter.out

import com.zzan.liquor.adapter.out.entity.LiquorEntity
import com.zzan.liquor.adapter.out.jpa.LiquorJpaRepository
import com.zzan.liquor.application.port.out.LiquorRepository
import com.zzan.liquor.domain.Liquor
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class LiquorPersistenceAdapter(
    private val liquorJpaRepository: LiquorJpaRepository
) : LiquorRepository {
    override fun findById(liquorId: String): Liquor? =
        liquorJpaRepository.findByIdOrNull(liquorId)?.toDomain()

    override fun save(liquor: Liquor): Liquor =
        liquorJpaRepository.save(LiquorEntity.of(liquor)).toDomain()

    override fun search(keyword: String, pageable: Pageable): Slice<Liquor> =
        liquorJpaRepository.findByNameContaining(keyword, pageable)
            .map { it.toDomain() }

    override fun update(liquor: Liquor) {
        liquorJpaRepository.findByIdOrNull(liquor.id!!)?.update(liquor)
    }
}
