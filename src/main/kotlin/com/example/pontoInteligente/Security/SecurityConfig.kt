package com.example.pontoInteligente.Security

import com.example.pontoInteligente.services.FuncionarioService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val funcionarioDetailsService: FuncionarioDetailsService) :
    WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(authenticationProvider())
    }

//    fun securityWebFilterChain(http: ServerHttpSecurity): ServerHttpSecurity.HttpBasicSpec? =
//        http
//            .csrf().disable()
//            .cors().disable()
//            .authorizeExchange()?.pathMatchers("/api/cadastrar-pj", "/api/cadastrar-pf")?.
//            permitAll()?.anyExchange()?.
//            authenticated()?.
//            and()?.httpBasic()

    fun configure(http: ServerHttpSecurity) {
        http.authorizeExchange()?.pathMatchers("/api/cadastrar-pj", "/api/cadastrar-pf")?.
        permitAll()?.anyExchange()?.
        authenticated()?.and()?.
        httpBasic()?.and()?.
        csrf()?.disable()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(funcionarioDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}