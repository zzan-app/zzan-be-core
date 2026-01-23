package com.zzan.liquor.application.port.out

import com.zzan.liquor.domain.Liquor
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface LiquorRepository {
    fun findById(liquorId: String): Liquor?
    fun save(liquor: Liquor): Liquor
    fun search(keyword: String, pageable: Pageable): Slice<Liquor>
    fun update(liquor: Liquor)
}
