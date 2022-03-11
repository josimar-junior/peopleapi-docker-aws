package com.jj.peopleapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean

@OpenAPIDefinition(
    info = Info(
        title = "People service API",
        version = "1.0",
        description = "Documentation of People service API"
    )
)
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(
                io.swagger.v3.oas.models.info.Info()
                    .title("People service API")
                    .version("1.0")
                    .license(
                        License()
                            .name("JJ Softwares")
                            .url("https://github.com/josimar-junior/peopleapi-docker-aws")
                    )
            )

}