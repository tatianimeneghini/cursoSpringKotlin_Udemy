package com.example.pontoInteligente.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SenhasUteis {
    fun gerarBCrypt(senha: String): String? = BCryptPasswordEncoder().encode(senha)
}