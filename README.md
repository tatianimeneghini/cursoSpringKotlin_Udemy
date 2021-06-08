# Ponto Inteligente
#### Projeto API Restful com Spring Boot, Kotlin e MongoDB
Realizado durante o curso ["API Restful com Spring Boot, Kotlin e MongoDB"](https://www.udemy.com/course/api-restful-kotlin-spring-boot-mongodb/), elaborado por [Márcio Souza](https://github.com/m4rciosouza), na plataforma Udemy.

## Ferramentas utilizadas
- Spring Boot (versão 2.2.)6;
- Kotlin (versão 1.4.2)1;
- Mongo DB;
- Spring Security (versão 5.2.2).

### Diferença de versão
As alterações necessárias foram realizadas baseadas na atualização das versões do Spring Boot 2.2.6.RELEASE e versão do Kotlin em 1.4.21.

## Etapas
### Build do projeto
- Build do projeto com Gradle, atalho `Ctrl + F9`.

### Rodar projeto
1. Inicializar o Banco de Dados do MongoDB;
2. Run arquivo `PontoInteligenteApplication.Kt` em `src/main/kotlin/com.example.pontoInteligente`.

#### Conexão com MongoDB
Através da linha de comando:
1. Inicializar o MongoDB com o comando `mongod`;
2. Inicializar o Mongo Shell com o comando `mongo`. 
   
Obs.: é possível utilizar alguns comandos do MongoDB para visualizar o banco de Dados `show databases`, entrar no db `use database` e visualizar as collections `show collections`.

Através do programa gráfico do MongoDB Compass ao conectar localmente: 
`mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false`.

## Requisições via Postman
Para enviar as requisições via Postman com a url `localhost:8080` e verificar nos arquivos da pasta `src/main/kotlin/com.example.pontoInteligente\controllers`.
* Authorization: inserir autenticação básica `Basic Auth`, com usuário `user` e senha que aparece quando a aplicação é iniciada.
* Verificar endpoints para cada método HTPP.
* Verificar o preenchimento do body em formato `json`.
* Security: para remover um lançamento será necessário logar no sistema como Admin (user e senha).

Abaixo encontra-se a tabela com todos endpoints disponíveis.

### Endpoints
| Método HTTP | Ação  | Endpoint  | Request Body |
|---|---|---|---|
| GET | Listar Funcionário por Id | `/lancamentos/funcionario/{id}`  | none |
| POST | Cadastro de Lançamento por FuncionárioId | `/lancamentos/{funcionarioId}` | `{ "data": "2021-05-31 18:37:00", "tipo": "INICIO_TRABALHO", "funcionarioId": "{funcionarioId}", "descricao": "" }` |
| GET | Listar Lançamentos por FuncionárioId | `/lancamentos/{funcionarioId}` |  none |
| PUT | Atualizar Lançamento por FuncionárioId  | `/lancamentos/{funcionarioId}` | `{ "data": "2021-05-31 18:37:00", "tipo": "INICIO_TRABALHO", "funcionarioId": "{funcionarioId}", "descricao": "" }` |
| DEL | Remover Lançamento por FuncionárioId | `/lancamentos/{funcionarioId}` | `{"data": "2021-05-31 18:37:00", "tipo": "INICIO_TRABALHO", "funcionarioId": "{funcionarioId}", "descricao": "Início de jornada de trabalho"}` |
| POST | Cadastrar Pessoa Jurídica | `/cadastrar-pj` | `{ "nome": "Admin", "email": "", "senha": "", "cpf": "", "cnpj": "", "razaoSocial": "" }` |
| POST | Cadastrar Pessoa Física | `/cadastrar-pf` | `{ "nome": "", "email": "", "senha": "", "cpf": "", "cnpj": "" }` |
| PUT | Atualizar Funcionário por FuncionárioId | `funcionarios/{funcionarioId}` | `{ "nome": "Admin", "email": "", "senha": "", "qtdHorasAlmoco": 1 }` |
| GET | Listar Empresa por CNPJ | `/empresas/cnpj/{cnpj}` | none |
| PUT | Atualizar Empresa por CNPJ | `/empresas/cnpj/{cnpj}` | `{ "razaoSocial": "", "cnpj": "", "id": "id" }` |