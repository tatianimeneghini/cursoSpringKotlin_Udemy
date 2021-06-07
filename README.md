# Ponto Inteligente
#### Projeto API Restful com Spring Boot, Kotlin e MongoDB
Realizado durante o curso ["API Restful com Spring Boot, Kotlin e MongoDB"](https://www.udemy.com/course/api-restful-kotlin-spring-boot-mongodb/), elaborado por [Márcio Souza](https://github.com/m4rciosouza), na plataforma Udemy.

### Diferença de versão
As alterações necessárias foram realizadas baseadas na atualização das versões do Spring Boot 2.2.6.RELEASE e versão do Kotlin em 1.4.21.

## Etapas
### Build do projeto
- [ ] Build do projeto com Gradle, atalho `Ctrl + F9`.

### Rodar projeto
1. Inicializar o MongoDB com na linha de comando `mongod`;
2. Inicializar o Mongo Shell na linha de comando `mongo`;
3. Run arquivo `PontoInteligenteApplication.Kt` em `src/main/kotlin/com.example.pontoInteligente`.

### Requisições via Postman
Para enviar as requisições via Postman com a url `localhost:8080` e verificar nos arquivos da pasta `src/main/kotlin/com.example.pontoInteligente\controllers`.
* Authorization: inserir autenticação básica `Basic Auth`, com usuário `user` e senha que aparece quando a aplicação é iniciada.
* Verificar endpoints para cada método HTPP.
* Verificar o preenchimento do body em formato `json`.