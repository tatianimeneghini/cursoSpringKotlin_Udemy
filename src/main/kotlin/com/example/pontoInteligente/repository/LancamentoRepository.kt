package com.example.pontoInteligente.repository

import com.example.pontoInteligente.document.Lancamento
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface LancamentoRepository : MongoRepository<Lancamento, String> {
    fun findByFuncionarioId(funcionarioId: String, pageable: PageRequest): Page<Lancamento>
}