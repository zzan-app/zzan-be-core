package com.zzan.common.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class ApiLoggingFilter : OncePerRequestFilter() {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val startTime = System.currentTimeMillis()

        try {
            filterChain.doFilter(request, response)
        } finally {
            val duration = System.currentTimeMillis() - startTime
            val logMessage = buildLog(request, response, duration)

            when {
                response.status >= 500 -> log.error(logMessage)
                response.status >= 400 -> log.warn(logMessage)
                else -> log.info(logMessage)
            }
        }
    }

    private fun buildLog(
        request: HttpServletRequest,
        response: HttpServletResponse,
        duration: Long
    ): String {
        val method = request.method
        val query = request.queryString?.let { "?$it" } ?: ""
        val path = "${request.requestURI}$query"
        val status = response.status
        val userId = getUserId() ?: "anonymous"

        return "[$method] $path - $status (${duration}ms) user=$userId {\"method\":\"$method\",\"path\":\"$path\",\"status\":$status,\"duration_ms\":$duration,\"user_id\":\"$userId\"}"
    }

    private fun getUserId(): String? {
        return try {
            SecurityContextHolder.getContext().authentication?.principal as? String
        } catch (e: Exception) {
            null
        }
    }
}
