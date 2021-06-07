package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.document.Lancamento
import com.example.pontoInteligente.dtos.LancamentoDto
import com.example.pontoInteligente.enum.TipoEnum
import com.example.pontoInteligente.response.Response
import com.example.pontoInteligente.services.FuncionarioService
import com.example.pontoInteligente.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
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
    val qtdPorPagina: Int = 15 //subscreve o valor

    // @PostMapping - indica o método HTTP do tipo POST.
    // @Valid - anotação que valida e executa as anotação @get do arquivo LancamentoDto.
    // @RequestBody - anotação que a requisição deve ter um body do tipo json.
    // result - armazena o resulta na variável do tipo da interface de result.
    // ResponseEntity - classe utilitária do Spring que retorna o código HTTP correto da requisição.
    // A função irá retornar a data class criada Response
    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult): ResponseEntity<Response<LancamentoDto>> {
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

    // @GetMapping - indica o método HTTP do tipo GET.
    // @PathVariable - anotação que indica que o parâmetro será passado através da url.
    // Objeto response do lançamento.
    // Buscar o id pelo Serviço do Lançamento que pode retornar nulo.
    // Tratamento do erro para lançamento nulo, adicionando um novo erro e retornar status HTTP 404.
    @GetMapping("/{id}")
    fun listarPorId(@PathVariable("id") id: String): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if (lancamento == null) {
            response.erros.add("Lançamento não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    // @RequestParam - anotação que indica o parâmetro da requisição.
    @GetMapping("/funcionario/{funcionarioId}")
    fun listarFuncionarioId(@PathVariable("funcionarioId") funcionarioId: String,
                            @RequestParam(value = "pag", defaultValue = "0") pag: Int,
                            @RequestParam(value = "ord", defaultValue = "id") ord: String,
                            @RequestParam(value = "dir", defaultValue = "DESC") dir: String
                            ): ResponseEntity<Response<Page<LancamentoDto>>> {
        val response: Response<Page<LancamentoDto>> = Response<Page<LancamentoDto>>()

        // Objeto para obter os dados do PageRequest.
        val pageRequest: PageRequest = PageRequest.of(pag, qtdPorPagina, Sort.Direction.valueOf(dir), ord)

        // Consulta do Serviço para buscar funcionário Id.
        val lancamentos: Page<Lancamento> = lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest)

        // Converter os dados de lançamento para Lançamento DTO.
        // map - intera todos os elementos da lista e realiza a ação de converterLancamentoDto.
        val lancamentoDto: Page<LancamentoDto> = lancamentos.map { lancamento ->
            converterLancamentoDto(lancamento)
        }

        // Definir os dados para o response e retorna o status HTTP 200.
        response.data = lancamentoDto
        return ResponseEntity.ok(response)
    }

    // @PutMapping - indica o método HTTP do tipo PUT.
    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: String,
                  @Valid @RequestBody lancamentoDto: LancamentoDto,
                  result: BindingResult): ResponseEntity<Response<LancamentoDto>> {
        val response: Response<LancamentoDto> = Response<LancamentoDto>()
        validarFuncionario(lancamentoDto, result)
        lancamentoDto.id = id
        val lancamento: Lancamento = converterDtoParaLancamento(lancamentoDto, result)

        if (result.hasErrors()) {
            result.allErrors.forEach { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
            }
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.persistir(lancamento)
        response.data = converterLancamentoDto(lancamento)
        return ResponseEntity.ok(response)
    }

    // @DeleteMapping - indica o método HTTP do tipo DELETE.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun remover(@PathVariable("id") id: String): ResponseEntity<Response<String>> {
        val response: Response<String> = Response<String>()
        val lancamento: Lancamento? = lancamentoService.buscarPorId(id)

        if (lancamento == null) {
            response.erros.add("Erro ao remover lançamento. Registro não encontrado para o id $id")
            return ResponseEntity.badRequest().body(response)
        }

        lancamentoService.remover(id)
        return ResponseEntity.ok(Response<String>())
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
    // Formatação dos dados pelo lançamento DTO.
    // dateFormat - formatar a data.
    private fun converterLancamentoDto(lancamento: Lancamento): LancamentoDto =
        LancamentoDto(dateFormat.format(lancamento.data),
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
            val lanc: Lancamento? = lancamentoService.buscarPorId(lancamentoDto.id!!)
            if (lanc == null) result.addError(ObjectError("lancamento",
                        "Lançamento não encontrado."))
        }
        // parse - método para converter o formato da data em uma string.
        return Lancamento(dateFormat.parse(lancamentoDto.data),
                TipoEnum.valueOf(lancamentoDto.tipo!!),
                lancamentoDto.funcionarioId!!,
                lancamentoDto.decricao,
                lancamentoDto.localizacao,
                lancamentoDto.id
        )
    }
}