package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.repository.FuncionarioRepository
import com.example.pontoInteligente.services.FuncionarioService
import org.springframework.stereotype.Service

@Service
class FuncionarioServiceImpl (val funcionarioRepository: FuncionarioRepository) : FuncionarioService {
    override fun persistir(funcionario: Funcionario): Funcionario =
        funcionarioRepository.save(funcionario)

    override fun buscarPorCpf(cpf: String): Funcionario? =
        funcionarioRepository.findByCpf(cpf)

    override fun buscarPorEmail(email: String): Funcionario? =
        funcionarioRepository.findByEmail(email)

    override fun buscarPorId(id: String): Funcionario? =
        funcionarioRepository.findById(id).get()
}