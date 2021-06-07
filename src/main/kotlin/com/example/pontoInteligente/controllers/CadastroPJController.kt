package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Empresa
import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.dtos.CadastroPJDto
import com.example.pontoInteligente.enum.PerfilEnum
import com.example.pontoInteligente.response.Response
import com.example.pontoInteligente.services.FuncionarioService
import com.example.pontoInteligente.utils.SenhasUteis
import com.pontointeligente.api.pontointeligenteapi.services.EmpresaService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/api/cadastrar-pj")
class CadastroPJController(val empresaService: EmpresaService,
                           val funcionarioService: FuncionarioService) {

    @PostMapping
    fun cadastrar(@Valid @RequestBody cadastroPJDto: CadastroPJDto,
                  result: BindingResult): ResponseEntity<Response<CadastroPJDto>> {
        val response: Response<CadastroPJDto> = Response<CadastroPJDto>()

        validarDadosExistentes(cadastroPJDto, result)
        if (result.hasErrors()) {
            result.allErrors.forEach { erro -> erro.defaultMessage?.let { response.erros.add(it) } }
        }

        val empresa: Empresa = converterDtoParaEmpresa(cadastroPJDto)
        empresaService.persistir(empresa)

        var funcionario: Funcionario = converterDtoParaFuncionario(cadastroPJDto, empresa)
        funcionarioService.persistir(funcionario)
        response.data = converterCadastroPJDto(funcionario, empresa)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastroPJDto: CadastroPJDto, result: BindingResult) {
        val empresa: Empresa? = empresaService.buscarPorCnpj(cadastroPJDto.cnpj)
        if (empresa != null) {
            result.addError(ObjectError("empresa", "Empresa já existente."))
        }

        val funcionarioCpf: Funcionario? = funcionarioService.buscarPorCpf(cadastroPJDto.cpf)
        if (funcionarioCpf != null) {
            result.addError(ObjectError("funcionario", "Funcionário já existente."))
        }

        val funcionarioEmail: Funcionario? = funcionarioService.buscarPorEmail(cadastroPJDto.email)
        if (funcionarioEmail != null) {
            result.addError(ObjectError("funcionario", "Email já existente."))
        }
    }

    private fun converterDtoParaEmpresa(cadastroPJDto: CadastroPJDto): Empresa =
        Empresa(cadastroPJDto.razaoSocial, cadastroPJDto.cnpj)

    private fun converterDtoParaFuncionario(cadastroPJDto: CadastroPJDto, empresa: Empresa) =
        Funcionario(cadastroPJDto.nome,
            cadastroPJDto.email,
            SenhasUteis().gerarBCrypt(cadastroPJDto.senha),
            cadastroPJDto.cpf,
            PerfilEnum.ROLE_ADMIN,
            empresa.id.toString()
        )

    private fun converterCadastroPJDto(funcionario: Funcionario, empresa: Empresa): CadastroPJDto =
        CadastroPJDto(funcionario.nome,
            funcionario.email,
            "",
            funcionario.cpf,
            empresa.cnpj,
            empresa.razaoSocial,
            funcionario.id
        )

}