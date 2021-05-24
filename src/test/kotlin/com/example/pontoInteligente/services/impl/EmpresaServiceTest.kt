package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Empresa
import com.example.pontoInteligente.repository.EmpresaRepository
import com.pontointeligente.api.pontointeligenteapi.services.EmpresaService
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.jvm.Throws

@SpringBootTest
class EmpresaServiceTest {
    //insere classe vazia para teste, sem alterar no banco de dados
    @MockBean
    private val empresaRepository: EmpresaRepository? = null
    private val CNPJ = "12345678900"

    @Autowired
    val empresaService: EmpresaService? = null

    @BeforeEach //executar antes do teste
    @Throws //lançar uma exceção no mockito
    fun setUp() {
        //para cada chamada da função findByCnpj, me retorne a empresa
        //caso a empresaRepository seja nula, não chama. Caso não seja nula, chame a função
        BDDMockito.given(empresaRepository?.findByCnpj(CNPJ)).willReturn(empresa())
        BDDMockito.given(empresaRepository?.save(empresa())).willReturn(empresa())
    }

    @Test
    fun `buscar empresa por CNPJ`() {
        val empresa: Empresa? = empresaService?.buscarPorCnpj(CNPJ)
        Assertions.assertNotNull(empresa)
    }

    @Test
    fun `persistir empresa`() {
        val empresa: Empresa? = empresaService?.persistir(empresa())
        Assertions.assertNotNull(empresa)
    }

    private fun empresa(): Empresa =
        Empresa("Razao Social", CNPJ, "1")
}