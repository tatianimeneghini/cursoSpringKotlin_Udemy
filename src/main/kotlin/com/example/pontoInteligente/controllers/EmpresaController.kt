package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Empresa
import com.example.pontoInteligente.dtos.CadastroPJDto
import com.example.pontoInteligente.dtos.EmpresaDto
import com.pontointeligente.api.pontointeligenteapi.services.EmpresaService
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/empresas")
class EmpresaController(val empresaService: EmpresaService) {

    @GetMapping("/cnpj/{cnpj}")
    fun buscarPorCnpj(@PathVariable("cnpj") cnpj: String): ResponseEntity<com.example.pontoInteligente.response.Response<EmpresaDto>> {
        val response: com.example.pontoInteligente.response.Response<EmpresaDto> = com.example.pontoInteligente.response.Response<EmpresaDto>()
        val empresa: Empresa? = empresaService.buscarPorCnpj(cnpj)

        if (empresa == null) {
            response.erros.add("Empresa não encontrada com o CNPJ ${cnpj}")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterEmpresaDto(empresa)
        return ResponseEntity.ok(response)

    }

    private fun converterEmpresaDto(empresa: Empresa): EmpresaDto =
        EmpresaDto(empresa.razaoSocial,
            empresa.cnpj,
            empresa.id
        )
}