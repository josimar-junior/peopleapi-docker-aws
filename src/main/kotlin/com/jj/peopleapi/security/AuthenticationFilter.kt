package com.jj.peopleapi.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jj.peopleapi.error.Errors.P401
import com.jj.peopleapi.repository.PersonRepository
import com.jj.peopleapi.security.exception.AuthenticationException
import com.jj.peopleapi.vo.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val personRepository: PersonRepository,
    private val jwtUtil: JwtUtil
) : UsernamePasswordAuthenticationFilter(authenticationManager){

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val id = personRepository.findByEmail(loginRequest.email)?.id
            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)
            return authenticationManager.authenticate(authToken)
        } catch (ex: Exception) {
            throw AuthenticationException(P401.message, P401.code)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(id)
        response.setHeader("Authorization", "Bearer $token")
    }
}