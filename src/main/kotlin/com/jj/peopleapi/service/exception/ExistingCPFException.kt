package com.jj.peopleapi.service.exception

class ExistingCPFException (override val message: String, val code: String): RuntimeException(message)