package com.example.pontoInteligente

import com.example.pontoInteligente.document.Empresa
import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.enum.PerfilEnum
import com.example.pontoInteligente.repository.EmpresaRepository
import com.example.pontoInteligente.repository.FuncionarioRepository
import com.example.pontoInteligente.repository.LancamentoRepository
import com.example.pontoInteligente.utils.SenhasUteis
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PontoInteligenteApplication (
	val empresaRepository: EmpresaRepository,
	val funcionarioRepository: FuncionarioRepository,
 	val lancamentoRepository: LancamentoRepository) : CommandLineRunner {
	// CommandLineRunner ajuda preparar o microsserviço ou enviar dados para outro microsserviço
	// e sempre executa um bloco de código antes de iniciar a aplicação.

	override fun run(vararg args: String?) {
		empresaRepository.deleteAll() //deletar toda empresa do Repository na aplicação
		funcionarioRepository.deleteAll()
		lancamentoRepository.deleteAll()

		var empresa: Empresa = Empresa(
			"Empresa",
			"1000234567890")
		empresa  = empresaRepository.save(empresa) //persistir os dados na base

		var admin: Funcionario = Funcionario(
			"Admin",
			"admin@empresa.com",
			SenhasUteis().gerarBCrypt("987654"),
			"12345678900",
			PerfilEnum.ROLE_ADMIN,
			empresa.id!!) //opcional
		admin = funcionarioRepository.save(admin)

		var funcionario: Funcionario = Funcionario(
			"Funcionario",
			"funcionario@empresa.com",
			SenhasUteis().gerarBCrypt("123456"),
			"44456789012",
			PerfilEnum.ROLE_USUARIO,
			empresa.id!!
		)
		funcionario = funcionarioRepository.save(funcionario)

		System.out.println("Empresa ID: " + empresa.id)
		System.out.println("Admin ID: " + admin.id)
		System.out.println("Funcionário ID: " + funcionario.id)
	}
}

fun main(args: Array<String>) {
	runApplication<PontoInteligenteApplication>(*args)
}
