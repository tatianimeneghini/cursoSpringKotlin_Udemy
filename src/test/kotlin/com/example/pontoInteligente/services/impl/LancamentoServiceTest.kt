package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Lancamento
import com.example.pontoInteligente.enum.TipoEnum
import com.example.pontoInteligente.repository.LancamentoRepository
import com.example.pontoInteligente.services.LancamentoService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

@SpringBootTest
class LancamentoServiceTest {
    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    @Autowired
    private val lancamentoService: LancamentoService? = null

    private val id: String = "1"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito
            .given<Page<Lancamento>>(lancamentoRepository?.findByFuncionarioId(id, PageRequest.of(0, 10)))
            .willReturn(PageImpl(ArrayList<Lancamento>()))
            //Retorna uma página
            //Page é uma interface. Classe real é PageImpl.
        BDDMockito.given(lancamentoRepository?.findById("1")).willReturn(Optional.of(lancamento()))
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java)))
            .willReturn(lancamento())

    }

    @Test
    fun testBuscarLancamentoPorFuncionarioId() {
        val lancamento: Page<Lancamento>? = lancamentoService?.buscarPorFuncionarioId(id, PageRequest.of(0,10))
        Assertions.assertNotNull(lancamento)
    }

    @Test
    fun testeBuscarLancamentoPorId() {
        val lancamento: Lancamento? = lancamentoService?.buscarPorId(id)
        Assertions.assertNotNull(lancamento)
    }

    @Test
    fun testePersistirLancamento() {
        val lancamento: Lancamento? = lancamentoService?.persistir(lancamento())
        Assertions.assertNotNull(lancamento)
    }

    private fun lancamento(): Lancamento = Lancamento(Date(), TipoEnum.INICIO_TRABALHO, id)
}