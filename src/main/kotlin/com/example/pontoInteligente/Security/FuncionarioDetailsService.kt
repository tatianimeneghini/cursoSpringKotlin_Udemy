package com.example.pontoInteligente.Security

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.services.FuncionarioService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// UserDetailsService - interface core que carrega as informações do usuário.
@Service
class FuncionarioDetailsService(val funcionarioService: FuncionarioService) : UserDetailsService {

    // loadUserByUsername - localiza o usuário baseado no username.
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val funcionario: Funcionario? = funcionarioService.buscarPorEmail(username)
            if (funcionario != null) {
                return FuncionarioPrincipal(funcionario)
            }
        }
        // throw - anotação indica quais exceções devem ser declaradas por uma função.
        // UsernameNotFoundException - construtor de username não encontrado que especifica a mensagem.
        throw UsernameNotFoundException(username)
    }
}