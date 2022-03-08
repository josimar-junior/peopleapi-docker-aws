package com.jj.peopleapi.error

import com.jj.peopleapi.error.Errors.P301
import com.jj.peopleapi.error.Errors.P001
import com.jj.peopleapi.service.exception.ExistingCPFException
import com.jj.peopleapi.service.exception.PersonNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class PeopleApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = exception.bindingResult.allErrors
        val apiErrors: List<APIError> = errors.map { APIError(P001.code, it.defaultMessage ?: P001.message) }.toList()
        val errorResponse: ErrorResponse = ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY, apiErrors)
        return ResponseEntity(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ExistingCPFException::class)
    fun handleExistingCPFException(exception: ExistingCPFException): ResponseEntity<ErrorResponse> {
        val errorResponse: ErrorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, APIError(exception.code, exception.message))
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PersonNotFoundException::class)
    fun handlePersonNotFoundException(exception: PersonNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse: ErrorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND, APIError(exception.code, exception.message))
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse: ErrorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, APIError(P301.code, P301.message))
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}