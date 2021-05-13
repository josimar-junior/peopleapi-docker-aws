package com.jj.peopleapi.resource

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.vo.PersonVO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/people")
class PersonResource (private val service: PersonService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<PersonVO>> {
        return ResponseEntity.ok(service.findAll()
            .stream()
            .map(Person::toVO)
            .collect(Collectors.toList()))
    }

    @PostMapping
    fun save(@Valid @RequestBody personVO: PersonVO): ResponseEntity<Void> {
        val personSaved: Person = service.save(personVO.toModel())
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(personSaved.id).toUri()
        return ResponseEntity.created(uri).build()
    }
}