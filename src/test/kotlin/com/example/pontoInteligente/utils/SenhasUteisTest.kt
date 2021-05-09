package com.example.pontoInteligente.utils

import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions

class SenhasUteisTest {
    private val SENHA = "1234"
    private val bCryptEncoder = BCryptPasswordEncoder()

    @Test
    fun `test gerar hash da senha`() {
        val hash = SenhasUteis().gerarBCrypt(SENHA)
        Assertions.assertTrue(bCryptEncoder.matches(SENHA, hash))
    }
}