package com.jj.peopleapi.repository

import com.jj.peopleapi.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long> {
    fun findByCpf(cpf: String): Person?
}