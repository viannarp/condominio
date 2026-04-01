# Condomínio Admin - Condomínio dos Cajueiros

Projeto simples para administração de condomínio com:

- cadastro de moradores
- lançamento de condomínio
- lançamento de cota extra
- registro de pagamento (incluindo pagamento parcial e saldo em aberto)
- emissão de recibo em tela para impressão
- geração de PDF de recibo no backend
- relatório mensal por unidade

## Tecnologias

- Java 17
- Spring Boot 4.0.5
- Spring MVC
- Thymeleaf
- Spring Data JPA
- Spring Security
- Sql Server Database
- Bootstrap 5.3.8

## Requisitos

- Java 17+
- Maven 3.6.3+

## Como executar

```bash
mvn spring-boot:run
```

Abra:

- http://localhost:8080

Login padrão:

- usuário: `admin`
- senha: `admin123`

Configuração SQL Server:

- JDBC URL: `jdbc:sqlserver://localhost:1433;DatabaseName=CondominioDb;encrypt=false`
- User: `sa`
- Password: `teste01`

## Estrutura principal

- `Morador`: cadastro do condômino
- `Lancamento`: débito do tipo condomínio ou cota extra
- `Pagamento`: registro de pagamento e base para recibo

## Funcionalidades implementadas

1. login com Spring Security
2. edição/exclusão de moradores, lançamentos e pagamentos
3. geração de PDF de recibo no backend
4. pagamentos parciais e saldo em aberto
5. relatório mensal por unidade
