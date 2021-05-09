package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Empresa

interface EmpresaService {
    fun buscarPorCnpj(cnpj: String): Empresa?

    fun persistir(empresa: Empresa): Empresa
}