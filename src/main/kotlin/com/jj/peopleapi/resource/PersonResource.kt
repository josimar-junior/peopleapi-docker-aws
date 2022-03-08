package com.jj.peopleapi.resource

import com.jj.peopleapi.extension.toModel
import com.jj.peopleapi.extension.toVO
import com.jj.peopleapi.model.Person
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.vo.PersonVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.validation.Valid

@Tag(name = "People endpoint")
@RestController
@RequestMapping("/people")
class PersonResource (private val service: PersonService) {

    @Operation(summary = "Find all people")
    @GetMapping
    fun findAll(@PageableDefault pageable: Pageable): ResponseEntity<Page<PersonVO>> =
        ResponseEntity.ok(service.findAll(pageable).map { it.toVO() })

    @Operation(summary = "Find person by id")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<PersonVO> =
         ResponseEntity.ok(service.findById(id)?.toVO())

    @Operation(summary = "Save person")
    @PostMapping
    fun save(@Valid @RequestBody personVO: PersonVO): ResponseEntity<Void> {
        val personSaved: Person = service.save(personVO.toModel())
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(personSaved.id).toUri()
        return ResponseEntity.created(uri).build()
    }
}