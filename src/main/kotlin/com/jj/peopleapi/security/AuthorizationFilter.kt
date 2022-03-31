package com.jj.peopleapi.security

import com.jj.peopleapi.error.Errors
import com.jj.peopleapi.security.exception.AuthenticationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetailsCustomService: UserDetailsCustomService
): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        if(authorization != null && authorization.startsWith("Bearer ")) {
            val auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtil.isValidToken(token))
            throw AuthenticationException(Errors.P403.message, Errors.P403.message)

        val subject = jwtUtil.getSubject(token)
        val person = userDetailsCustomService.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(person, null, person.authorities)
    }
}