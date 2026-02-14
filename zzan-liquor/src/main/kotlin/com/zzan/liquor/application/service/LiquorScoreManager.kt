package com.zzan.liquor.application.service

import com.zzan.liquor.application.port.out.LiquorRepository
import org.springframework.stereotype.Service

@Service
class LiquorScoreManager(
    private val liquorRepository: LiquorRepository,
) {
    fun addScore(liquorId: String, score: Double) {
        val liquor = liquorRepository.findById(liquorId) ?: return
        liquorRepository.update(liquor.addScore(score))
    }

    fun updateScore(liquorId: String, oldScore: Double, newScore: Double) {
        val liquor = liquorRepository.findById(liquorId) ?: return
        liquorRepository.update(liquor.updateScore(oldScore, newScore))
    }

    fun removeScore(liquorId: String, score: Double) {
        val liquor = liquorRepository.findById(liquorId) ?: return
        liquorRepository.update(liquor.removeScore(score))
    }
}
