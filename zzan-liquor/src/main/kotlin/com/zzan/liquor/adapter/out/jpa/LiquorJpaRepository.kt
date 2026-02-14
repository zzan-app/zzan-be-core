package com.zzan.liquor.adapter.out.jpa

import com.zzan.liquor.adapter.out.entity.LiquorEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface LiquorJpaRepository : JpaRepository<LiquorEntity, String> {
    fun findByNameContaining(name: String, pageable: Pageable): Slice<LiquorEntity>
}
