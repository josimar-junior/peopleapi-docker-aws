package com.jj.peopleapi.repository

import com.jj.peopleapi.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PersonRepository: JpaRepository<Person, Long> {
    fun findByCpf(cpf: String): Optional<Person>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Person?
}