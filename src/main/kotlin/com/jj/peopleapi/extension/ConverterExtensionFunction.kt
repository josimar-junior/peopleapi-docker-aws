package com.jj.peopleapi.extension

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.vo.PersonVO

fun Person.toVO(): PersonVO =
    PersonVO(this.id, this.name, this.cpf, this.email)

fun PersonVO.toModel(): Person =
    Person(this.id, this.name, this.cpf, this.email)