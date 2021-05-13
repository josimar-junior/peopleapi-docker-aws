package com.jj.peopleapi.vo

import com.jj.peopleapi.model.Person
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class PersonVO (

    var id: Long? = null,

    @field: NotBlank(message = "Name is required")
    var name: String = "",

    @field: NotBlank(message = "CPF is required")
    @field: CPF(message = "Invalid CPF")
    var cpf: String = "",

    @field: Email(message = "Invalid email")
    var email: String? = null,

) {
    fun toModel(): Person = Person(this.id, this.name, this.cpf, this.email)
}