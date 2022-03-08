package com.jj.peopleapi.vo

import com.jj.peopleapi.validation.EmailAvailable
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
    @EmailAvailable
    var email: String? = null,

)