# ProjectBank

![Java](https://www.oracle.com/a/tech/img/cb88-java-logo-001.jpg)

## Descrição

O **ProjectBank** é uma aplicação bancária simples desenvolvida em Java utilizando o framework Spring Boot. Este projeto segue o padrão de arquitetura MVC (Model-View-Controller) e permite a criação, consulta, depósito, saque, encerramento de contas e transferências via PIX.

## Funcionalidades

- **Criação de Conta**: Permite a criação de uma nova conta bancária.
- **Consulta de Contas**: Permite a consulta de todas as contas ou de uma conta específica pelo número da conta ou CPF do titular.
- **Depósito**: Permite realizar depósitos em uma conta existente.
- **Saque**: Permite realizar saques de uma conta existente.
- **Encerramento de Conta**: Permite encerrar uma conta existente.
- **Transferência via PIX**: Permite realizar transferências entre contas utilizando o sistema PIX.

## Solução

O ProjectBank foi desenvolvido com o objetivo de fornecer uma solução simples e eficiente para a gestão de contas bancárias. Utilizando o Spring Boot, conseguimos criar uma aplicação robusta e escalável, que segue os princípios do MVC para separar as responsabilidades de cada camada da aplicação.

### Diferenciais

- **Tratamento de Erros**: A aplicação trata de forma adequada os erros comuns, como conta não encontrada e valores inválidos para operações financeiras, retornando respostas HTTP apropriadas.
- **Simulação do Sistema PIX**: Implementação do sistema de transferências via PIX, permitindo transações rápidas e seguras entre contas.
- **Documentação e Código Limpo**: Código bem documentado e organizado, seguindo as melhores práticas de desenvolvimento em Java e Spring Boot.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
