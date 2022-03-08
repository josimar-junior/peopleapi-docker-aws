package com.jj.peopleapi.service.exception

class PersonNotFoundException(override val message: String, val code: String): RuntimeException(message)