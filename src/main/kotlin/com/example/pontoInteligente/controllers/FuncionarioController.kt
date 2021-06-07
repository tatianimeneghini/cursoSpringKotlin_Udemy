package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.dtos.FuncionarioDto
import com.example.pontoInteligente.response.Response
import com.example.pontoInteligente.services.FuncionarioService
import com.example.pontoInteligente.utils.SenhasUteis
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/funcionarios")
class FuncionarioController(val funcionarioService: FuncionarioService) {

    @PutMapping("/{id}")
    fun atualizar (@PathVariable("id") id: String,
                   @Valid @RequestBody funcionarioDto: FuncionarioDto,
                    result: BindingResult): ResponseEntity<Response<FuncionarioDto>> {
        val response: Response<FuncionarioDto> = Response<FuncionarioDto>()
        val funcionario: Funcionario? = funcionarioService.buscarPorId(id)

        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado."))
        }

        if (result.hasErrors()) {
            result.allErrors.forEach { erro ->
                erro.defaultMessage?.let { response.erros.add(it) }
            }
            return ResponseEntity.badRequest().body(response)
        }

        val funcaoAtualizar: Funcionario = atualizarDadosFuncionario(funcionario!!, funcionarioDto)
        funcionarioService.persistir(funcaoAtualizar)
        response.data = converterFuncionarioDto(funcionario)

        return ResponseEntity.ok(response)
    }

    private fun atualizarDadosFuncionario(funcionario: Funcionario,
                                          funcionarioDto: FuncionarioDto): Funcionario {
        val senha: String
        if (funcionarioDto.senha == null) {
            senha = funcionario.senha
        } else {
            senha = SenhasUteis().gerarBCrypt(funcionarioDto.senha)
        }

        return Funcionario(funcionarioDto.nome,
            funcionario.email,
            senha,
            funcionario.cpf,
            funcionario.perfil,
            funcionario.empresaId,
            funcionarioDto.valorHora?.toDouble(),
            funcionarioDto.qtdHorasTrabalhoDia?.toFloat(),
            funcionarioDto.qtdHorasAlmoco?.toFloat(),
            funcionario.id
        )
    }

    private fun converterFuncionarioDto(funcionario: Funcionario): FuncionarioDto =
        FuncionarioDto(funcionario.nome,
            funcionario.email,
            "",
            funcionario.valorHora.toString(),
            funcionario.qtdHorasTrabalhoDia.toString(),
            funcionario.qtdHorasAlmoco.toString(),
            funcionario.id
        )
}