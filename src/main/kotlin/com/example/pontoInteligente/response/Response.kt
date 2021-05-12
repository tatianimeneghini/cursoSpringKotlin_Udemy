package com.example.pontoInteligente.response

//Tipo de dado <T>
data class Response<T>(
    //array do tipo string = criação de lista
    val erros: ArrayList<String> = arrayListOf(),
    var data: T? = null
)