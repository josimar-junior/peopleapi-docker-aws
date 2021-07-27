package com.jj.peopleapi.resource

import com.jj.peopleapi.model.Person
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.vo.PersonVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.stream.Collectors
import javax.validation.Valid

@Tag(name = "People endpoint")
@RestController
@RequestMapping("/people")
class PersonResource (private val service: PersonService) {

    @Operation(summary = "Find all people")
    @GetMapping
    fun findAll(): ResponseEntity<List<PersonVO>> {
        return ResponseEntity.ok(service.findAll()
            .stream()
            .map(Person::toVO)
            .collect(Collectors.toList()))
    }

    @Operation(summary = "Find person by id")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PersonVO> {
        val person = service.findById(id)
        return if (person == null)
            ResponseEntity.notFound().build()
        else ResponseEntity.ok(person.toVO())
    }

    @Operation(summary = "Save person")
    @PostMapping
    fun save(@Valid @RequestBody personVO: PersonVO): ResponseEntity<Void> {
        val personSaved: Person = service.save(personVO.toModel())
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(personSaved.id).toUri()
        return ResponseEntity.created(uri).build()
    }
}