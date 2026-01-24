package com.zzan.common.security

import com.zzan.common.dto.TokenUserInfo
import com.zzan.common.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractTokenFromRequest(request)

        try {
            if (token != null && jwtUtil.isValidToken(token) && jwtUtil.isAccessToken(token)) {
                setAuthenticationContext(token)
            }
            filterChain.doFilter(request, response)
        } finally {
            SecurityContextHolder.clearContext()
        }
    }

    private fun extractTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    private fun setAuthenticationContext(token: String) {
        val userId = jwtUtil.getUserIdFromToken(token)
        val role = jwtUtil.getRoleFromToken(token)

        if (userId != null && role != null) {
            val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))

            val authentication = UsernamePasswordAuthenticationToken(
                userId,
                null,
                authorities
            ).apply {
                details = TokenUserInfo(userId = userId, role = role)
            }

            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}
