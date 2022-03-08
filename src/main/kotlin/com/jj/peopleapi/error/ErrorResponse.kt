package com.jj.peopleapi.error

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import org.springframework.http.HttpStatus

@JsonAutoDetect(fieldVisibility = ANY)
class ErrorResponse {

    var statusCode: Int
    var errors: List<APIError>

    constructor(statusCode: Int, errors: List<APIError>) {
        this.statusCode = statusCode
        this.errors = errors
    }

    companion object {
        fun of(status: HttpStatus, errors: List<APIError>) =
            ErrorResponse(status.value(), errors)

        fun of(status: HttpStatus, error: APIError) =
            ErrorResponse(status.value(), listOf(error))
    }
}

@JsonAutoDetect(fieldVisibility = ANY)
class APIError(val code: String, val message: String)