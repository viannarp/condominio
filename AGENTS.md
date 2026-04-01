# Condomínio Admin - Base do Projeto

Projeto simples para administração de condomínio com:

- adicionar login com Spring Security
- permitir editar/excluir registros
- cadastro de moradores
- lançamento de condomínio
- lançamento de cota extra
- registro de pagamento
- controlar pagamentos parciais e saldo em aberto
- adicionar relatório mensal por unidade
- emissão de recibo em tela para impressão ou salvar em PDF
- gerar PDF real no backend


Preciso que crie um modulo para lançamento das despesas mensais
com lançamento das despesas fixas e despesas extras
preciso de um relatorio dessas despesas mensais

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
- `Lancamento`: débito do tipo condomínio ou cota extra, despesas fixas e despesas extras
- `Pagamento`: registro do pagamento e base para recibo e registro das despesas pagas

## Melhorias recomendadas

1. adicionar login com Spring Security
2. permitir editar/excluir registros
3. gerar PDF real no backend
4. controlar pagamentos parciais e saldo em aberto
5. adicionar relatório mensal por unidade
6. adicionar um módulo para lançamento das despesas do condomínio
7. adicionar relatório mensal por tipo de despesas
8. adicionar relatório mensal das despesas do condomínio


## Banco de dados
1. Criação tabela tipos de despesas
2. Criação tabela pagamento das despesas

## Observação

Para manter a base simples, o recibo é uma página pronta para impressão. O navegador pode salvar como PDF.
