package com.example.pontoInteligente.dtos

//Data Object Transfer
data class EmpresaDto(
    val razaoSocial: String,
    val cnpj: String,
    val id: String? = null
)