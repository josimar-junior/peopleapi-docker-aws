package com.jj.peopleapi.config

import com.jj.peopleapi.model.enums.Role
import com.jj.peopleapi.repository.PersonRepository
import com.jj.peopleapi.security.AuthenticationFilter
import com.jj.peopleapi.security.AuthorizationFilter
import com.jj.peopleapi.security.JwtUtil
import com.jj.peopleapi.security.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val personRepository: PersonRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter() {

    private val PEOPLE_MATCHERS = arrayOf(
        "/people"
    )

    private val OPENAPI_MATCHERS = arrayOf(
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/swagger-ui/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(*OPENAPI_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PEOPLE_MATCHERS).permitAll()
            .antMatchers(HttpMethod.GET, *PEOPLE_MATCHERS).hasAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()
            .and()
            .addFilter(AuthenticationFilter(authenticationManager(), personRepository, jwtUtil))
            .addFilter(AuthorizationFilter(authenticationManager(), jwtUtil, userDetails))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}