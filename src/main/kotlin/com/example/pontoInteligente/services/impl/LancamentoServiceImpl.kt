package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Lancamento
import com.example.pontoInteligente.repository.LancamentoRepository
import com.example.pontoInteligente.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository) : LancamentoService {
    override fun buscarPorFuncionarioId(funcionarioId: String, pageRequest: PageRequest): Page<Lancamento> =
        lancamentoRepository.findByFuncionarioId(funcionarioId,pageRequest)

    override fun buscarPorId(id: String): Lancamento? =
        lancamentoRepository.findById(id).get()

    override fun persistir(lancamento: Lancamento): Lancamento =
        lancamentoRepository.save(lancamento)

    override fun remover(id: String) =
        lancamentoRepository.deleteById(id)
}