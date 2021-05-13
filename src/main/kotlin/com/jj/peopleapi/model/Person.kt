package com.jj.peopleapi.model

import com.jj.peopleapi.vo.PersonVO
import javax.persistence.*

@Entity
@Table(name = "person")
data class Person (

    @Id
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String = "",

    @Column(nullable = false, length = 14)
    var cpf: String = "",

    var email: String? = null,

) {
    fun toVO(): PersonVO = PersonVO(this.id, this.name, this.cpf, this.email)
}