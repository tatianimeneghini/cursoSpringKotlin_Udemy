//package com.example.pontoInteligente.config
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//
//@Configuration
//class SecurityConfig : WebSecurityConfigurerAdapter() {
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests().antMatchers("/**").authenticated()
//            .and().httpBasic().and().csrf().disable()
//    }
//
//}