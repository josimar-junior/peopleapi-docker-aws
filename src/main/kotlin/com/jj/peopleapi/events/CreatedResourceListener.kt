package com.jj.peopleapi.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletResponse

@Component
class CreatedResourceListener {

    @EventListener
    fun listen(createdResourceEvent: CreatedResourceEvent) {
        val response = createdResourceEvent.response
        val id = createdResourceEvent.id
        addHeaderLocation(response, id)
    }

    private fun addHeaderLocation(response: HttpServletResponse, id: Long) {
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()
        response.setHeader("Location", uri.toASCIIString())
    }
}