# API Inteligente com Reconhecimento de Fala e Spring Boot (Budgeting API)

Este projeto é uma API inteligente de gerenciamento de orçamento (Budgeting API) desenvolvida com **Spring Boot**, **Spring AI** e **OpenAI**. A aplicação é capaz de processar comandos de voz em áudio, transcrevê-los para texto, entender a intenção do usuário (como registrar despesas ou consultar gastos), interagir com o banco de dados através de funções da aplicação (Tool Calling) e gerar uma resposta de voz de volta para o usuário.

Durante este desafio, consegui aprofundar em conceitos cruciais para o desenvolvimento moderno de APIs inteligentes:
- **Spring AI:** Entendimento prático de como orquestrar modelos de linguagem (LLMs), modelos de voz (TTS) e transcrição (Speech-to-Text) com a mesma fluidez de outros módulos do ecossistema Spring.
- **Tool Calling (Function Calling):** A habilidade de permitir que uma IA decida de forma autônoma quando invocar métodos e lógica reais do backend para concluir o comando de um usuário, unindo processamento de linguagem natural com execução de código real.
- **DDD e Arquitetura Limpa:** Manter a separação rígida de responsabilidades de forma que a IA seja apenas um canal de entrada (Interface de Usuário inteligente) que consome os mesmos Casos de Uso que a API REST, sem poluir as regras de negócio centrais.
