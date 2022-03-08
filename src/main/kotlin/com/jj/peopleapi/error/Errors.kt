package com.jj.peopleapi.error

enum class Errors(val code: String, val message: String) {
    P001("P-001", "Invalid field"),
    P101("P-101", "CPF %s already exists"),
    P201("P-201", "Person not found"),
    P301("P-301", "Sorry, internal server error, try again later")
}