package com.jj.peopleapi.resource

import com.jj.peopleapi.events.CreatedResourceEvent
import com.jj.peopleapi.extension.toModel
import com.jj.peopleapi.extension.toVO
import com.jj.peopleapi.model.Person
import com.jj.peopleapi.security.UserIsAdminOrLoggedIn
import com.jj.peopleapi.service.PersonService
import com.jj.peopleapi.vo.PersonRequestVO
import com.jj.peopleapi.vo.PersonResponseVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Tag(name = "People endpoint")
@RestController
@RequestMapping("/people")
class PersonResource (
    private val service: PersonService,
    private val publisher: ApplicationEventPublisher
) {

    @Operation(summary = "Find all people")
    @GetMapping
    fun findAll(@PageableDefault pageable: Pageable): ResponseEntity<Page<PersonResponseVO>> =
        ResponseEntity.ok(service.findAll(pageable).map { it.toVO() })

    @Operation(summary = "Find person by id")
    @GetMapping("/{id}")
    @UserIsAdminOrLoggedIn
    fun findById(@PathVariable id: Long): ResponseEntity<PersonResponseVO> =
         ResponseEntity.ok(service.findById(id)?.toVO())

    @Operation(summary = "Save person")
    @PostMapping
    fun save(@Valid @RequestBody personVO: PersonRequestVO, response: HttpServletResponse): ResponseEntity<PersonResponseVO> {
        val personSaved: Person = service.save(personVO.toModel())
        publisher.publishEvent(CreatedResourceEvent(this, response, personSaved.id!!))
        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved.toVO())
    }
}