package com.jj.peopleapi.service

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.service.exception.ExistingCPFException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import kotlin.jvm.Throws

interface PersonService {
    fun findAll(pageable: Pageable): Page<Person>
    fun findById(id: Long): Person?

    @Throws(ExistingCPFException::class)
    fun save(person: Person): Person
    fun emailAvailable(email: String): Boolean
}