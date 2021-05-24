package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.document.Lancamento
import com.example.pontoInteligente.dtos.LancamentoDto
import com.example.pontoInteligente.enum.TipoEnum
import com.example.pontoInteligente.response.Response
import com.example.pontoInteligente.services.FuncionarioService
import com.example.pontoInteligente.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import javax.validation.Valid

// @RestController - anotação que faz o Spring reconhecer a classe como controller.
// @RequestMapping - anotação do endpoint do controller.
@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(
    val lancamentoService: LancamentoService,
    val funcionarioService: FuncionarioService
) {
    private val dateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss")

    // @Value - anotação do valor vindo do application.properties
    @Value("\${paginacao.qntd_por_pagina}")
    val qntd_por_pagina: Int = 15 //subscreve o valor

    // @PostMapping - indica o método HTTP do tipo post.
    // @Valid - anotação que valida e executa as anotação @get do arquivo LancamentoDto.
    // @RequestBody - anotação que a requisição deve ter um body do tipo json.
    // result - armazena o resulta na variável do tipo da interface de result.
    // ResponseEntity - classe utilitária do Spring que retorna o código HTTP correto da requisição.
    // A função irá retornar a data class criada Response
    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult
    ): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)

        // Tratamento do erro com hasErrors que retorna um booleano.
        // Caso haja erro, irá popular cada erro em todas mensagens de erro, deve adicionar a mensagem padrão.
        // O retorno deve ser o código 400 de Bad Request, com o body da resposta.
        if (result.hasErrors()) {
            result.allErrors.forEach { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
            }
            return ResponseEntity.badRequest().body(response)
        }

        // Implementação da função que converte o DTO para Lançamento.
        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)
        lancamentoService.persistir(lancamento)
        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    private fun validarFuncionario(lancamentoDto: LancamentoDto, result: BindingResult) {
        // Caso o ID do funcionário no lançamento DTO seja nulo, adiciona erro.
        if (lancamentoDto.funcionarioId == null) {
            result.addError(ObjectError("funcionario",
                "Funcionário não informado."
            ))
            return
        }
        // Caso a busca por ID do funcionário seja nulo, adiciona erro de ID inexistente.
        val funcionario: Funcionario? = funcionarioService.buscarPorId(lancamentoDto.funcionarioId)
        if (funcionario == null) {
            result.addError(ObjectError("funcionario",
                "Funcionário não encontrado. ID inexistente!"
            ))
        }
    }

    // Instrução única de instanciação do objeto LancamentoDto, é possível condensar com o igual, sem as chaves.
    // dateFormat - formatar a data.
    private fun converterLancamentoDto(lancamento: Lancamento): LancamentoDto =
        LancamentoDto(dateFormat.format(
            lancamento.data),
            lancamento.tipo.toString(),
            lancamento.descricao,
            lancamento.localizacao,
            lancamento.funcionarioId,
            lancamento.id
        )

    private fun converterDtoParaLancamento(lancamentoDto: LancamentoDto,
                                           result: BindingResult): Lancamento {
        // Se o id for diferente de nulo, buscar por ID através do serviço de Lançamento.
        // Se o id retornado for nulo, adicionar erro no resultado com mensagem padrão.
        if (lancamentoDto.id != null) {
            val lanc: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id)
            if (lanc == null) result.addError(ObjectError("lancamento",
                        "Lançamento não encontrado."))
        }
        // parse - método para converter o formato da data em uma string.
        return Lancamento(dateFormat.parse(
                lancamentoDto.data),
                TipoEnum.valueOf(lancamentoDto.tipo!!),
                lancamentoDto.funcionarioId!!,
                lancamentoDto.decricao,
                lancamentoDto.localizacao,
                lancamentoDto.id
        )
    }
}