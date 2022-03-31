package com.jj.peopleapi.error

enum class Errors(val code: String, val message: String) {
    P001("P-001", "Invalid field"),
    P101("P-101", "CPF %s already exists"),
    P201("P-201", "Person not found"),
    P301("P-301", "Sorry, internal server error, try again later"),
    P401("P-401", "Authentication failure"),
    P402("P-402", "Incorrect username and/or passwords"),
    P403("P-403", "Invalid token")
}