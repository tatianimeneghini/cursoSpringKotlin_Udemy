package com.example.pontoInteligente.services.impl

import com.example.pontoInteligente.document.Funcionario
import com.example.pontoInteligente.enum.PerfilEnum
import com.example.pontoInteligente.repository.FuncionarioRepository
import com.example.pontoInteligente.utils.SenhasUteis
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class FuncionarioServiceTest {
    @Autowired
    private val funcionarioService: FuncionarioService? = null

    @MockBean
    private val funcionarioRepository: FuncionarioRepository? = null
    private val email: String = "email@email.com"
    private val cpf: String = "34234855948"
    private val id: String = "1"
    private val senha: String = "123456"

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java)))
            .willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findById(id)).willReturn(Optional.of(funcionario()))
        BDDMockito.given(funcionarioRepository?.findByEmail(email)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByCpf(cpf)).willReturn(funcionario())
    }

    @Test
    fun testPersistirFuncionario() {
        val funcionario: Funcionario? = funcionarioService?.persistir(funcionario())
        Assertions.assertNotNull(funcionario)
    }

    fun funcionario(): Funcionario =
        Funcionario(
            "Nome",
            email,
            SenhasUteis().gerarBCrypt(senha).let { senha },
            cpf,
            PerfilEnum.ROLE_USUARIO,
            id
        )
}