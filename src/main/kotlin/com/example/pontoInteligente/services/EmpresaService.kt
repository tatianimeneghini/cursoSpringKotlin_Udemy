package com.pontointeligente.api.pontointeligenteapi.services

import com.example.pontoInteligente.document.Empresa

interface EmpresaService {

    fun buscarPorCnpj(cnpj: String): Empresa?

    fun persistir(empresa: Empresa): Empresa

}