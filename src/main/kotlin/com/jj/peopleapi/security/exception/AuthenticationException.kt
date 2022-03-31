package com.jj.peopleapi.security.exception

class AuthenticationException(override val message: String, val code: String): Exception(message)