package com.example.pontoInteligente.services

import com.example.pontoInteligente.document.Funcionario

interface FuncionarioService {
    fun persistir(funcionario: Funcionario): Funcionario

    fun buscarPorCpf(cpf: String): Funcionario?

    fun buscarPorEmail(email: String): Funcionario?

    fun buscarPorId(id: String): Funcionario?
}