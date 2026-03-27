# Condomínio Admin - Base do Projeto

Projeto simples para administração de condomínio com:

- cadastro de moradores
- lançamento de condomínio
- lançamento de cota extra
- registro de pagamento
- emissão de recibo em tela para impressão ou salvar em PDF

## Tecnologias

- Java 17
- Spring Boot 4.0.5
- Spring MVC
- Thymeleaf
- Spring Data JPA
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

Configuração SQL Server:

- JDBC URL: `jdbc:sqlserver://localhost:1433;DatabaseName=CondominioDb;encrypt=false`
- User: `sa`
- Password: `teste01`

## Estrutura principal

- `Morador`: cadastro do condômino
- `Lancamento`: débito do tipo condomínio ou cota extra
- `Pagamento`: registro do pagamento e base para recibo

## Melhorias recomendadas

1. adicionar login com Spring Security
2. permitir editar/excluir registros
3. gerar PDF real no backend
4. controlar pagamentos parciais e saldo em aberto
5. adicionar relatório mensal por unidade

## Observação

Para manter a base simples, o recibo é uma página pronta para impressão. O navegador pode salvar como PDF.
