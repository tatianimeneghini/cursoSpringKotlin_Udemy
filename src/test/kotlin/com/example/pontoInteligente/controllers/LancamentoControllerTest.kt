package com.example.pontoInteligente.controllers

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.document.Lancamento
import com.example.pontoInteligente.dtos.LancamentoDto
import com.example.pontoInteligente.enum.PerfilEnum
import com.example.pontoInteligente.enum.TipoEnum
import com.example.pontoInteligente.services.FuncionarioService
import com.example.pontoInteligente.services.LancamentoService
import com.example.pontoInteligente.utils.SenhasUteis
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

// @SpringBootTest - anotação que cria o contexto de teste.
// @AutoConfigureMockMvc - anotação que cria um mock de teste.
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureDataMongo
class LancamentoControllerTest {
    @Autowired
    private val mvc: MockMvc? = null

    // @MockBean - anotação que cria um objeto falso para manipular o retorno dele.
    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val urlBase: String = "/api/lancamentos/"
    private val idFuncionario: String = "1"
    private val idLancamento: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    // @Test - anotação que indica método de teste no Spring.
    // @Throws - anotação que indica ser possível lançar alguma exceção caso aconteça.
    // @WithMockUser - anotação que informa um usuário qualquer como teste.
    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testeCadastrarLancamento() {
        val lancamento: Lancamento = obterDadosLancamento()

        var funcionarioTeste = Funcionario(
            "Ana",
            "ana@email.com",
            SenhasUteis().gerarBCrypt("1234").let { it },
            "345.684.920-99",
            PerfilEnum.ROLE_USUARIO,
            "2"
        )

        // BDDMockito - framework que cria um objeto mock.
        // given - quando o método buscarPorId do Serviço do Funcionário for chamado.
        // willReturn - deve retornar um objeto qualquer.
        BDDMockito
            .given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
            .willReturn(funcionarioTeste)
        BDDMockito
            .given(lancamentoService?.persistir(obterDadosLancamento()))
            .willReturn(lancamento)

        // mvc - variável que trata sobre uma anotação do MockMvc.
        // perform - envia uma requisição e retorna o tipo esperado.
        // post - método HTTP POST.
        // content - insere o conteúdo da requisição.
        // contentType - insere o tipo do conteúdo no header da requisição.
        // accept - insere o aceite do tipo de conteúdo no header.
        // andExpect - verificações desejadas.
        // jsonPath - acessa o objeto json de uma maneira simplificada com a raiz "$".
        mvc!!.perform(MockMvcRequestBuilders.post(urlBase)
            .content(obterJsonRequisicaoPost())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.tipo").value(tipo))
            .andExpect(jsonPath("$.data.data").value(dateFormat.format(data)))
            .andExpect(jsonPath("$.data.funcionarioId").value(idFuncionario))
            .andExpect(jsonPath("$.erros").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser(username = "admin@admin.com.br", roles = arrayOf("ADMIN"))
    fun testeRemoverLancamento() {
        BDDMockito
            .given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
            .willReturn(obterDadosLancamento())

        // delete - método HTTP DELETE.
        mvc!!.perform(MockMvcRequestBuilders.delete(urlBase + idLancamento)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
    }

    // ObjectMapper - cria um objeto.
    // writeValueAsString - converte um objeto em string.
    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(): String {
        val lancamentoDto: LancamentoDto = LancamentoDto(
            dateFormat.format(data),
            tipo,
            "Descriçãawm",
            "1.234.4.234",
            idFuncionario
        )
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDto)
    }

    // Função para criar um lançamento.
    private fun obterDadosLancamento(): Lancamento =
        Lancamento(data,
            TipoEnum.valueOf(tipo),
            idFuncionario,
            "Descrição",
            "1.243,4.345",
            idLancamento
        )
}