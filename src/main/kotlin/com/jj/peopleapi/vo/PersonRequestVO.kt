package com.jj.peopleapi.vo

import com.jj.peopleapi.validation.EmailAvailable
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class PersonRequestVO (

    var id: Long? = null,

    @field: NotBlank(message = "Name is required")
    var name: String,

    @field: NotBlank(message = "CPF is required")
    @field: CPF(message = "Invalid CPF")
    var cpf: String,

    @field: Email(message = "Invalid email")
    @field: NotBlank(message = "Email is required")
    @EmailAvailable
    var email: String,

    @field: NotBlank(message = "Password is required")
    var password: String
)