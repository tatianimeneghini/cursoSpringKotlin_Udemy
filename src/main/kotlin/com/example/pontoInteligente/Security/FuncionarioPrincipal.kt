package com.example.pontoInteligente.Security

import com.example.pontoInteligente.document.Funcionario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// UserDetails - interface básica com user e senha (informação cor do usuário).
// Encapsula objetos de autenticação.
class FuncionarioPrincipal(val funcionario: Funcionario) : UserDetails {

    // getAuthorities - returna os objetos do authorities granted para o usuário.
    // GrantedAuthority - representa o authority granted para uma autenticação do objeto.
    // authorities será uma lista mutável do perfil de autenticação.
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(funcionario.perfil.toString()))
        return authorities
    }

    // isEnabled - usuário está ativo ou inativo.
    // Não utilizamos essa verificação, por isso mantemos por padrão o valor "true".
    override fun isEnabled(): Boolean = true

    // getUsername - retorna o username utilizado.
    override fun getUsername(): String = funcionario.email

    // isCredentialsNonExpired - indica a credential (senha) do usuário está expirada.
    // Não utilizamos essa verificação, por isso mantemos por padrão o valor "true".
    override fun isCredentialsNonExpired(): Boolean = true

    // getPassword - retorna a senha utilizada.
    override fun getPassword(): String = funcionario.senha

    // isAccountNonExpired - indica se a conta não está expirada.
    // Não utilizamos essa verificação, por isso mantemos por padrão o valor "true".
    override fun isAccountNonExpired(): Boolean = true

    // isAccountNonLocked - indica se a conta não está bloqueada.
    // Não utilizamos essa verificação, por isso mantemos por padrão o valor "true".
    override fun isAccountNonLocked(): Boolean = true
}