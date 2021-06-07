package com.example.pontoInteligente.Security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

@Configuration
class SecurityConfig(val funcionarioDetailService: FuncionarioDetailService) :
    WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(authenticationProvider())
    }

    fun configure(http: ServerHttpSecurity?) {
        http?.
            authorizeExchange()?.
            pathMatchers("/api/cadastrar-pj", "/api/cadastrar-pf")?.
            permitAll()?.
            anyExchange()?.
            authenticated()?.
            and()?.httpBasic()//?.
//            and()?.sessionManagement()?.
//            sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.and()?.
//            csrf()?.disable()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(funcionarioDetailService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    private fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}