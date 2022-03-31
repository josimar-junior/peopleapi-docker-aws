package com.jj.peopleapi.extension

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.vo.PersonRequestVO
import com.jj.peopleapi.vo.PersonResponseVO

fun Person.toVO(): PersonResponseVO =
    PersonResponseVO(this.id!!, this.name, this.cpf, this.email)

fun PersonRequestVO.toModel(): Person =
    Person(this.id, this.name, this.cpf, this.email, this.password)