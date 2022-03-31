package com.jj.peopleapi.model

import com.jj.peopleapi.model.enums.Role
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "person")
data class Person (

    @Id
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    @Column(nullable = false, length = 14)
    var cpf: String,

    var email: String,

    var password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "person_roles", joinColumns = [JoinColumn(name = "person_id")])
    var roles: Set<Role> = setOf()

)