package com.jj.peopleapi.service

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.service.exception.ExistingCPFException
import kotlin.jvm.Throws

interface PersonService {
    fun findAll(): List<Person>
    fun findById(id: Long): Person?

    @Throws(ExistingCPFException::class)
    fun save(person: Person): Person
}