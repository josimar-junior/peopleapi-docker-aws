package com.jj.peopleapi.events

import org.springframework.context.ApplicationEvent
import javax.servlet.http.HttpServletResponse

class CreatedResourceEvent(
    source: Any, val response: HttpServletResponse, val id: Long
) : ApplicationEvent(source)