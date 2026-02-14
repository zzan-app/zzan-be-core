package com.zzan.liquor.application.port.out

import com.zzan.liquor.domain.Liquor
import org.springframework.data.domain.Pageable

interface LiquorScrapRepository {
    fun getList(userId: String, cursor: String?, pageable: Pageable): List<Liquor>
    fun getExist(userId: String, liquorId: String): Boolean
    fun create(userId: String, liquorId: String)
    fun delete(userId: String, liquorId: String)
}
