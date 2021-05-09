package com.example.pontoInteligente.repository

import com.example.pontoInteligente.document.Funcionario
import org.springframework.data.mongodb.repository.MongoRepository

interface FuncionarioRepository : MongoRepository<Funcionario, String> {
    fun findByEmail(email: String): Funcionario?

    fun findByCpf(cpf: String): Funcionario?
}