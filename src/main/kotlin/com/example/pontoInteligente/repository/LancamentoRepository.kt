package com.example.pontoInteligente.repository

import com.example.pontoInteligente.document.Lancamento
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.repository.MongoRepository
import java.awt.print.Pageable

interface LancamentoRepository : MongoRepository<Lancamento, String> {
    fun findByFuncionariosId(funcionariosId: String, pageable: Pageable): Page<Lancamento?>

}