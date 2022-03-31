package com.jj.peopleapi.security

import com.jj.peopleapi.model.Person
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val person: Person) : UserDetails {

    val id: Long = person.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        person.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String = person.password

    override fun getUsername(): String = id.toString()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}