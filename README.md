# API Inteligente com Reconhecimento de Fala e Spring Boot (Budgeting API)

Este projeto é uma API inteligente de gerenciamento de orçamento (Budgeting API) desenvolvida com **Spring Boot**, **Spring AI** e **OpenAI**. A aplicação é capaz de processar comandos de voz em áudio, transcrevê-los para texto, entender a intenção do usuário (como registrar despesas ou consultar gastos), interagir com o banco de dados através de funções da aplicação (Tool Calling) e gerar uma resposta de voz de volta para o usuário.

Tudo isso mantendo uma arquitetura limpa (Clean Architecture / Domain-Driven Design - DDD) em camadas bem definidas.

---

## 🛠️ Tecnologias Utilizadas

- **Java 26** (configurado via Gradle Toolchain)
- **Spring Boot 4.0.5**
- **Spring AI (v2.0.0-M4)** com integrações:
  - **Transcription API (OpenAI Whisper)** para Speech-to-Text
  - **ChatClient / ChatModel (GPT-4o-mini)** para processamento e Tool Calling
  - **Speech API (TTS-1)** para Text-to-Speech
- **Spring Data JPA** & **MySQL** (com suporte automático via Docker Compose em desenvolvimento)
- **H2 Database** (para execução de testes sem necessidade de Docker)
- **Lombok** (para otimização de boilerplate)
- **JUnit 5** & **Mockito** (para testes unitários e de integração)

---

## 🚀 Melhorias Implementadas no Desafio

Como parte do desafio de projeto, foram desenvolvidas as seguintes evoluções na aplicação original:

1. **Expansão do Modelo de Categorias (`Category.java`):**
   - Foram adicionadas novas categorias para cobrir mais tipos de transações diárias: `ENTERTAINMENT` (entretenimento), `UTILITIES` (contas básicas), `DINING_OUT` (comer fora) e `OTHERS` (outros).

2. **Validações no Domínio (`Transaction.java`):**
   - Implementação de validações robustas diretamente no construtor do Domínio para garantir a consistência das transações:
     - O valor do gasto (`amount`) deve ser sempre maior que zero (valores inválidos lançam `IllegalArgumentException`).
     - A descrição e a categoria da transação são campos obrigatórios.

3. **Novo Caso de Uso - Cálculo de Gasto Total (`GetTotalSpendUseCase.java`):**
   - Criada a lógica para somar e retornar o total de despesas acumuladas, seja o total geral ou filtrado por uma categoria específica.
   - Exposta como uma ferramenta inteligente (`@Tool(name = "get-total-spend")`) para que o modelo de IA possa responder a perguntas como *"Quanto eu gastei com carro no total?"* ou *"Qual o meu gasto total até agora?"*.

4. **Novo Endpoint REST:**
   - Adicionado o endpoint `GET /transactions/total` que aceita um parâmetro opcional `category` para consulta manual ou integração externa.

5. **Independência nos Testes Automatizados (Banco de Dados H2):**
   - Adicionada configuração de H2 em escopo de teste para permitir que a suite de testes rode 100% com sucesso sem depender de uma instância ativa de Docker.
   - Atualizado o compilador no `build.gradle` para Java 26, alinhando a compilação local com o ambiente de execução moderno.

6. **Testes Automatizados:**
   - Criação de novos testes unitários para validar a lógica de validação do domínio (`TransactionValidationTest`) e de cálculo de gastos (`GetTotalSpendUseCaseTest`).

---

## 🏗️ Estrutura do Projeto

O projeto segue as diretrizes do DDD e da Clean Architecture:

- **`domain`**: Contém o modelo rico de domínio (`Transaction`, `Category`), identificadores fortemente tipados e contratos do repositório (`TransactionRepository`).
- **`application`**: Casos de uso de negócio da aplicação (`PersistTransactionUseCase`, `ListTransactionsByCategoryUseCase`, `GetTotalSpendUseCase`) que são expostos como ferramentas (`@Tool`) para o modelo de IA.
- **`infrastructure`**:
  - Adaptador HTTP (`TransactionController`)
  - Adaptador JPA (`JpaTransactionRepository` e entidades JPA)
  - Configurações da API de IA e prompts do sistema (`system-message.st`).

---

## ⚙️ Como Executar a Aplicação

### Pré-requisitos
- Docker instalado e em execução (para subir o MySQL automaticamente via Spring Boot Docker Compose).
- Variável de ambiente `OPENAI_API_KEY` configurada com sua chave da OpenAI.

### Passos para Rodar:

1. **Configurar a chave da API da OpenAI:**
   ```bash
   # No Windows (PowerShell)
   $env:OPENAI_API_KEY="sua_chave_aqui"
   ```

2. **Executar a aplicação:**
   ```bash
   ./gradlew bootRun
   ```
   *Nota: O Docker Compose subirá automaticamente o banco de dados MySQL.*

3. **Executar os testes:**
   ```bash
   ./gradlew test
   ```

---

## 🧪 Como Testar o Fluxo Principal

### 1. Criar Transação via REST
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"description": "Combustível Viagem", "category": "AUTO", "amount": 15000}'
```

### 2. Consultar Gasto Total via REST
```bash
# Gasto total geral
curl http://localhost:8080/transactions/total

# Gasto total de uma categoria específica
curl http://localhost:8080/transactions/total?category=AUTO
```

### 3. Interagir por Voz com a IA (Speech-to-Text -> LLM -> Text-to-Speech)
Envie um arquivo de áudio (`.m4a`, `.mp3` ou `.wav`) no endpoint `/transactions/ai`. A API processará o áudio e retornará uma resposta em formato de áudio MP3:

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@caminho/para/seu/audio.mp3" \
  --output resposta.mp3
```
*Frases de teste recomendadas:*
- *"Gastei 50 reais com combustível hoje"* -> Irá acionar `persist-transaction` na categoria `AUTO`.
- *"Quanto eu gastei com alimentação no total?"* -> Irá acionar `get-total-spend` filtrado pela categoria `DINING_OUT`.
- *"Qual meu gasto total geral?"* -> Irá acionar `get-total-spend`.

---

## 🧠 Aprendizados Obtidos

Durante este desafio, pudemos aprofundar em conceitos cruciais para o desenvolvimento moderno de APIs inteligentes:
- **Spring AI:** Entendimento prático de como orquestrar modelos de linguagem (LLMs), modelos de voz (TTS) e transcrição (Speech-to-Text) com a mesma fluidez de outros módulos do ecossistema Spring.
- **Tool Calling (Function Calling):** A habilidade de permitir que uma IA decida de forma autônoma quando invocar métodos e lógica reais do backend para concluir o comando de um usuário, unindo processamento de linguagem natural com execução de código real.
- **DDD e Arquitetura Limpa:** Manter a separação rígida de responsabilidades de forma que a IA seja apenas um canal de entrada (Interface de Usuário inteligente) que consome os mesmos Casos de Uso que a API REST, sem poluir as regras de negócio centrais.
