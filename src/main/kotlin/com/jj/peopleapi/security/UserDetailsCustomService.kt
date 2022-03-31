package com.jj.peopleapi.security

import com.jj.peopleapi.error.Errors.P402
import com.jj.peopleapi.repository.PersonRepository
import com.jj.peopleapi.security.exception.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val personRepository: PersonRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val person = personRepository.findById(id.toLong())
            .orElseThrow { AuthenticationException(P402.message, P402.code) }
        return UserCustomDetails(person)
    }
}