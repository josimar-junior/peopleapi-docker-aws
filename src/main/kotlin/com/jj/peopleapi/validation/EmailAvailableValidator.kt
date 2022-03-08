package com.jj.peopleapi.validation

import com.jj.peopleapi.service.PersonService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(private val personService: PersonService) : ConstraintValidator<EmailAvailable, String> {

    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean {
        if(email.isNullOrBlank()) return false
        return personService.emailAvailable(email)
    }
}