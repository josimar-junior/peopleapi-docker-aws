package com.jj.peopleapi.service.impl

import com.jj.peopleapi.error.Errors.P101
import com.jj.peopleapi.error.Errors.P201
import com.jj.peopleapi.model.Person
import com.jj.peopleapi.model.enums.Role
import com.jj.peopleapi.repository.PersonRepository
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.service.exception.ExistingCPFException
import com.jj.peopleapi.service.exception.PersonNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl(
    private val repository: PersonRepository,
    private val bCrypt: BCryptPasswordEncoder
) : PersonService {

    override fun findAll(pageable: Pageable): Page<Person> =
        repository.findAll(pageable)

    override fun findById(id: Long): Person? =
        repository.findById(id).orElseThrow { PersonNotFoundException(P201.message, P201.code) }

    override fun save(person: Person): Person {
        verifyIfPersonExistsWithCpf(person.cpf)
        val personCopy = person.copy(
            roles = setOf(Role.PERSON),
            password = bCrypt.encode(person.password)
        )
        return repository.save(personCopy)
    }

    override fun emailAvailable(email: String): Boolean {
        return !repository.existsByEmail(email)
    }

    private fun verifyIfPersonExistsWithCpf(cpf: String) {
        val personAux = repository.findByCpf(cpf)
        if (personAux.isPresent) throw ExistingCPFException(P101.message.format(cpf), P101.code)
    }
}