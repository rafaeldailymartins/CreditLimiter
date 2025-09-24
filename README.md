# Credit Limiter
![Static Badge](https://img.shields.io/badge/java-21-blue?logo=openjdk)
![Static Badge](https://img.shields.io/badge/docker-28.3-blue?logo=docker)
![Static Badge](https://img.shields.io/badge/SQLServer-17.5-blue)
![Static Badge](https://img.shields.io/badge/spring-3.5.6-blue?logo=spring)

O **Credit Limiter** é uma API RESTFul que implementa um CRUD simples com Spring Boot.


## 📋 Dependências

- [Docker](https://www.docker.com/)
- [Java](https://www.java.com/)
- [Gradle](https://gradle.org/)

## 🚀 Como executar

Recomendo usar o [IntelliJ IDEA](https://www.jetbrains.com/idea/) como IDE. Instale a IDE nos canais oficiais, e clone o repositório via IntelliJ, ele irá carregar corretamente o projeto.

Crie um novo arquivo application.yml. Você pode usar o arquivo application.template.yml para copiar todo o conteúdo. Recomendo trocar a jwt.secret por uma nova senha protegida e segura.

Faça o build e execute o programa. Você deve ter o `Docker` rodando na sua máquina para que o contâiner do SQL Server suba.

Tente acessar a raiz do projeto em `http://localhost:8080/` se a seguinte mensagem aparecer: `Credit Limiter Application`. Párabens, o projeto está rodando.

## 👨‍💻 Autenticando e usando a api

Sempre que a aplicação subir, caso não exista, será criado um novo usuário admin com as credenciais que estão no application.yml. 

Pela rota `/auth/login` você pode realizar a sua autenticação com o usuário admin criado, você deve passar username e password no corpo da requisição, a resposta será uma string de um token JWT

Agora, com o seu token em mãos, você deve usá-lo em todas as rotas que pedem autenticação, basta passar um cabeçalho com `Authorization: Bearer {seuTokenJwt}`.

## 👨‍💻 Autor

Criado e mantido por:

| [<img src="https://avatars.githubusercontent.com/u/162728324?v=4" width="60px;"/><br /><sub><b>Rafael Daily</b></sub>](https://github.com/rafaeldailymartins)
| :---: |
