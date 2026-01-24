package com.zzan.common.logging

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
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
        val method = request.method
        val uri = request.requestURI
        val queryString = request.queryString?.let { "?$it" } ?: ""

        try {
            filterChain.doFilter(request, response)
        } finally {
            val duration = System.currentTimeMillis() - startTime
            val status = response.status

            when {
                status >= 500 -> log.error { "[$method] $uri$queryString - $status (${duration}ms)" }
                status >= 400 -> log.warn { "[$method] $uri$queryString - $status (${duration}ms)" }
                else -> log.info { "[$method] $uri$queryString - $status (${duration}ms)" }
            }
        }
    }
}
