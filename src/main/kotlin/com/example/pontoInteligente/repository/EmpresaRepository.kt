package com.example.pontoInteligente.repository

import com.example.pontoInteligente.document.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String> { //type,
    // Convenção do nome do método
    fun findByCnpj(cnpj: String): Empresa?
}