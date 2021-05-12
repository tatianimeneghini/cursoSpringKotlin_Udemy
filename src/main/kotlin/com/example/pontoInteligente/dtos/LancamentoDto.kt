package com.example.pontoInteligente.dtos

data class LancamentoDto (
//    @get:NotEmpty(message = "Data não pode estar vazia.")
    val data: String? = null,

//    @get:NotEmpty(message = "Tipo não pode estar vazia.")
    val tipo: String? = null,

    val decricao: String? = null,
    val localizacao: String? = null,
    val funcionarioId: String? = null,
    val id: String? = null
)