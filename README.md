## <p align="center"> Car Parking API </p>

<p align="center"> <a href="#-sobre-o-projeto">Sobre</a> • <a href="#-tecnologias-utilizadas">Tecnologias</a> • <a href="#-arquitetura-e-estrutura">Arquitetura</a> • <a href="#-instalação-e-configuração">Instalação</a> • <a href="#-documentação-interativa-swagger">Documentação</a> • <a href="#-endpoints-da-api">Endpoints</a> • <a href="#-próximos-passos-todo">To-Do</a>
</p>

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-red?style=for-the-badge&logo=lombok&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-blue?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA1MCA1MCI+PHBhdGggZmlsbD0iI0YzMjYyNiIgZD0iTTcuNDg2IDI0Ljg3NWMwLTQuMDc5IDMuMzA5LTcuMzg4IDcuMzg4LTcuMzg4czcuMzg4IDMuMzA5IDcuMzg4IDcuMzg4LTMuMzA5IDcuMzg4LTcuMzg4IDcuMzg4LTcuMzg4LTMuMzA5LTcuMzg4LTcuMzg4eiIvPjxwYXRoIGZpbGw9IiM3QjEyMTYiIGQ9Ik0zMC4wNjcgMTcuNDg2YzQuMDc5IDAgNy4zODggMy4zMDkgNy4zODggNy4zODhzLTMuMzA5IDcuMzg4LTcuMzg4IDcuMzg4LTcuMzg4LTMuMzA5LTcuMzg4LTcuMzg4IDMuMzA5LTcuMzg4IDcuMzg4LTcuMzg4eiIvPjxwYXRoIGZpbGw9IiMxNjcwOUQiIGQ9Ik0yNC44NzUgMzcuNTE0YzAtNC4wNzkgMy4zMDktNy4zODggNy4zODgtNy4zODhzNy4zODggMy4zMDkgNy4zODggNy4zODgtMy4zMDkgNy4zODgtNy4zODggNy4zODgtNy4zODgtMy4zMDktNy4zODgtNy4zODh6Ii8+PHBhdGggZmlsbD0iIzc0MzYzMiIgZD0iTTI0Ljg3NSA3LjQ4NmMwIDQuMDc5LTMuMzA5IDcuMzg4LTcuMzg4IDcuMzg4cy03.Mzg4LTMuMzA5LTcuMzg4LTcuMzg4IDMuMzA5LTcuMzg4IDcuMzg4LTcuMzg4IDcuMzg4IDMuMzA5IDcuMzg4IDcuMzg4eiIvPjxwYXRoIGZpbGw9IiM1MDUwNTAiIGQ9Ik00Mi41MTQgMjQuODc1YzAgNC4wNzktMy4zMDkgNy4zODgtNy4zODggNy4zODhzLTcuMzg4LTMuMzA5LTcuMzg4LTcuMzg4IDMuMzA5LTcuMzg4IDcuMzg4LTcuMzg4IDcuMzg4IDMuMzA5IDcuMzg4IDcuMzg4eiIvPjxwYXRoIGZpbGw9IiNDMTkzMjkiIGQ9Ik0xNy40ODYgMzcuNTE0YzAtNC4wNzktMy4zMDktNy4zODgtNy4zODgtNy4zODhzLTcuMzg4IDMuMzA5LTcuMzg4IDcuMzg4IDMuMzA5IDcuMzg4IDcuMzg4IDcuMzg4IDcuMzg4LTMuMzA5IDcuMzg4LTcuMzg4eiIvPjwvc3ZnPg==&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## 📖 Sobre o Projeto

Este projeto é um sistema de gerenciamento de estacionamento de carros (`car-parking`) robusto e manutenível, desenvolvido com base nos princípios de Domain-Driven Design (DDD) e arquitetura de Monólito Modular. Ele foi projetado para gerenciar o fluxo completo de veículos em um estacionamento.

As **funcionalidades principais** incluem:
*   **Registro de entrada e saída de veículos:** Gerenciamento do ciclo de vida de um veículo no estacionamento.
*   **Alocação de vagas:** Controle da ocupação e disponibilidade das vagas.
*   **Faturamento (Billing) com base no tempo de permanência:** Cálculo automatizado das taxas de estacionamento.
*   **Notificações:** Envio de alertas e comunicações relevantes.
*   **Fluxo de Saída de Veículos e Pagamento (Refatorado):** Um processo de múltiplas etapas que garante que a finalização da saída do veículo ocorra somente após a confirmação bem-sucedida do pagamento. Este fluxo envolve a iniciação da saída, a criação de um registro de faturamento, uma requisição de pagamento externa e, por fim, a confirmação da saída.

A comunicação entre os módulos é predominantemente assíncrona, orquestrada via eventos de domínio, garantindo baixo acoplamento e flexibilidade.

## 🚀 Tecnologias Utilizadas

O projeto utiliza uma stack moderna baseada no ecossistema Spring para Java:

-   **Java 21**: Linguagem de programação principal.
-   **Spring Boot**: Framework líder para construção de aplicações Java robustas e escaláveis.
-   **Spring Data JPA / Hibernate**: Para persistência de dados e abstração do acesso ao banco de dados.
-   **Lombok**: Reduz a verbosidade do código Java com anotações úteis (e.g., `@Getter`, `@Setter`, `@RequiredArgsConstructor`).
-   **MapStruct**: Um gerador de código para mapeamento de objetos (DTOs para entidades de domínio e vice-versa), garantindo performance e segurança de tipo.
-   **SpringDoc OpenAPI (Swagger)**: Para geração automática e documentação interativa da API REST.
-   **Maven**: Ferramenta de automação de construção e gerenciamento de dependências.

## ⚙️ Arquitetura e Estrutura

A aplicação segue um padrão arquitetural de **Monólito Modular** e princípios de **Domain-Driven Design (DDD)**. O código é organizado em módulos de negócio independentes, cada um com suas responsabilidades claras e camadas bem definidas:

```text
src/main/java/com/example/carparking/
├── allocation/ # Módulo de gerenciamento de vagas
│   ├── application/ # Orquestração de casos de uso (DTOs, Mappers, UseCases)
│   ├── domain/ # Lógica de negócio principal (Entidades: Slot)
│   └── infrastructure/ # Detalhes técnicos (Persistence: SlotRepository, Web: Controllers)
├── billing/ # Módulo de faturamento e pagamento
│   ├── application/ # Orquestração de casos de uso (DTOs, Mappers, UseCases)
│   ├── domain/ # Lógica de negócio principal (Entidades: BillingRecord)
│   └── infrastructure/ # Detalhes técnicos (Persistence: BillingRepository, Web: Controllers)
├── common/ # Módulo com classes de uso geral (Exceções, Handlers)
│   ├── exception/
│   └── handlers/
├── entry/ # Módulo de registro de entrada e saída de veículos
│   ├── application/ # Orquestração de casos de uso (DTOs, Mappers, UseCases)
│   ├── domain/ # Lógica de negócio principal (Entidades: ParkingEntry)
│   └── infrastructure/ # Detalhes técnicos (Persistence: ParkingEntryRepository, Web: Controllers)
├── event/ # Módulo contendo as definições dos eventos de domínio
│   └── ... (PaymentFailedEvent, PaymentSuccessfulEvent, VehicleEnteredEvent, etc.)
└── notification/ # Módulo de serviços de notificação
```

### Comunicação entre Módulos

A comunicação entre os módulos é realizada de forma assíncrona através de **eventos de domínio**, como `VehicleEnteredEvent`, `VehicleExitInitiatedEvent`, e `PaymentSuccessfulEvent`. Isso garante um baixo acoplamento e facilita a orquestração de processos de negócio complexos, como sagas.

## 🛠️ Instalação e Configuração

### 💻 Desenvolvimento Local

#### Pré-requisitos
-   **Java 21** instalado.
-   **Maven** (gerenciado pelo `mvnw` do projeto).
-   Um banco de dados compatível com JPA (e.g., H2 para desenvolvimento, PostgreSQL, MySQL, etc.).

#### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-repositorio>
    cd car-parking
    ```

2.  **Compile e Execute a Aplicação:**
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
    A aplicação estará disponível em `http://localhost:8080`.

## 📚 Documentação Interativa (Swagger)

A API possui documentação interativa gerada automaticamente pelo SpringDoc OpenAPI (Swagger UI). Você pode acessá-la em:

-   **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 🔌 Endpoints da API

Aqui estão alguns dos endpoints principais disponíveis:

### 🚗 Gerenciamento de Vagas (`allocation`)
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/slots` | Cria uma nova vaga de estacionamento |
| `GET` | `/api/slots` | Lista todas as vagas disponíveis ou ocupadas |
| `GET` | `/api/slots/{id}/available` | Verifica a disponibilidade de uma vaga específica |

### 🎫 Entrada e Saída de Veículos (`entry`)
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/entries` | Registra a entrada de um veículo |
| `GET` | `/api/entries` | Lista todos os registros de entrada de veículos |
| `POST` | `/api/exits/initiate` | Inicia o processo de saída de um veículo |
| `POST` | `/api/exits/finalize` | Finaliza a saída de um veículo após confirmação de pagamento |

### 💳 Faturamento e Pagamento (`billing`)
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/payments` | Processa o pagamento para um registro de faturamento |
| `GET` | `/api/billings/{id}` | Recupera um registro de faturamento específico |

## 📝 Próximos Passos (To-Do)

- [x] Remover imports desnecessários em todo o projeto.
- [x] Adicionar testes unitários para todos os UseCases e Controllers.
- [ ] Documentar interativamente todos os controllers com anotações do Swagger (OpenAPI).
- [x] Dividir ambientes de desenvolvimento (Dev e Prod)
- [x] Adicionar banco de dados postgres no ambiente de Dev e Prod.
- [ ] Implementar uma camada de segurança JWT na aplicação.
- [ ] Implementar RateLimit para evitar ataques de DDOS (juntamente da camada de segurança).
- [ ] Implementar cache com redis.
- [x] Criar docker compose para orquestrar aos containers.


## 🤝 Contribuição

1.  Faça um Fork do projeto.
2.  Crie uma Branch para sua Feature (`git checkout -b feature/NomeDaSuaFeature`).
3.  Faça suas alterações e garanta que os testes estejam passando.
4.  Abra um Pull Request, descrevendo claramente suas modificações e o problema que ele resolve.

---
<p align="center"> Desenvolvido com ☕  e ❤️ por Thalisson.
</p>
