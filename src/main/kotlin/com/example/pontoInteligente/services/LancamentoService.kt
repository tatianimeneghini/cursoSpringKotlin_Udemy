package com.example.pontoInteligente.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import com.example.pontoInteligente.document.Lancamento

interface LancamentoService {
    fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest): Page<Lancamento>

    fun buscarPorId(id: String): Lancamento?

    fun persistir(lancamento: Lancamento): Lancamento

    fun remover(id: String)
}