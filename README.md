# 💰 Sistema de Finanças 

API REST para gerenciamento de finanças pessoais — controle de contas, categorias e transações, com relatórios de gastos e autenticação segura via JWT.

Projeto desenvolvido como portfólio, aplicando práticas reais de desenvolvimento backend: arquitetura em camadas, autenticação e autorização, testes automatizados, documentação de API e containerização.

## 🛠️ Tecnologias

- **Java 17** + **Spring Boot** (Spring Web, Spring Data JPA, Spring Security)
- **MySQL** — persistência de dados
- **JWT (JJWT)** — autenticação stateless
- **Swagger / OpenAPI (Springdoc)** — documentação interativa da API
- **JUnit 5 + Mockito** — testes unitários
- **Docker + Docker Compose** — containerização da aplicação e do banco
- **Maven** — gerenciamento de dependências

## ✨ Funcionalidades

- Cadastro e autenticação de usuários (login com JWT)
- CRUD completo de contas, categorias e transações
- Relatórios financeiros com queries JPQL (JOIN + GROUP BY):
  - Total de gastos por categoria
  - Saldo por conta (receitas x despesas)
- Rotas protegidas por token, com regras de acesso público/privado
- Testes unitários cobrindo as regras de negócio principais

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

```
Controller  →  Service  →  Repository  →  Banco de Dados
```

- **Controller** — expõe os endpoints REST
- **Service** — concentra as regras de negócio
- **Repository** — comunicação com o banco via Spring Data JPA
- **DTO** — objetos de transferência de dados, isolando a API do modelo interno
- **Security** — autenticação JWT, filtro de requisições e configuração de acesso

## 🚀 Como rodar o projeto

O projeto está totalmente containerizado — basta ter o **Docker** instalado.

```bash
git clone https://github.com/seu-usuario/sistema-financas.git
cd sistema-financas
docker compose up --build
```

A aplicação sobe em `http://localhost:8080`, junto com o banco de dados MySQL.

### Documentação interativa (Swagger)

Com o projeto rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Lá é possível visualizar e testar todos os endpoints diretamente pelo navegador.

### Autenticação

1. Crie um usuário: `POST /usuarios`
2. Faça login: `POST /auth/login` — retorna um token JWT
3. Use o token no header `Authorization: Bearer <token>` para acessar as rotas protegidas

## 🧪 Rodando os testes

```bash
./mvnw test
```

## 👤 Autor

**Kauã Orcia**


