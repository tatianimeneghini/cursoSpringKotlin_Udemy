package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Empresa
import com.example.pontoInteligente.repository.EmpresaRepository
import com.kazale.pontointeligente.api.pontointeligenteapi.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl (val empresaRepository: EmpresaRepository) : EmpresaService {
    override fun buscarPorCnpj(cnpj: String) =
        empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa) =
        empresaRepository.save(empresa)
}