package com.api.auth.security

import com.api.auth.jwtUtil.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RefreshTokenValidationFilter(
    private val jwtProvider: JwtProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
            val refreshToken = request.getHeader("Authorization")?.substring("Bearer ".length)
            if (refreshToken != null && jwtProvider.validateToken(refreshToken)) {
                val address = jwtProvider.getClaimsFromToken(refreshToken)?.getClaim("address")
                val authentication = UsernamePasswordAuthenticationToken(
                    address, null, null
                )
                SecurityContextHolder.getContext().setAuthentication(authentication)
                filterChain.doFilter(request, response)
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token")
            }
    }
}
