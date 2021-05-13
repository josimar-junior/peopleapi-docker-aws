package com.jj.peopleapi.service.impl

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.repository.PersonRepository
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.service.exception.ExistingCPFException
import org.springframework.stereotype.Service

@Service
class PersonServiceImpl (private val repository: PersonRepository): PersonService {

    override fun findAll(): List<Person> = repository.findAll()

    override fun save(person: Person): Person {
        verifyIfPersonExistsWithCpf(person)
        return repository.save(person)
    }

    private fun verifyIfPersonExistsWithCpf(person: Person) {
        val personAux = repository.findByCpf(person.cpf)
        if(personAux != null) throw ExistingCPFException("Existing cpf")
    }
}