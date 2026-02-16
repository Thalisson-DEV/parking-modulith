# Guia de Desenvolvimento Assistido por IA (GEMINI.md)

Este documento serve como um guia para qualquer assistente de IA que trabalhe neste projeto. Ele estabelece os padrões, a arquitetura e as expectativas para garantir que todas as contribuições sejam consistentes e de alta qualidade.

## 1. Visão Geral do Projeto

*   **Objetivo:** O projeto é um sistema de gerenciamento de estacionamento de carros (`car-parking`).
*   **Funcionalidades Principais:**
    *   Registro de entrada e saída de veículos.
    *   Alocação de vagas.
    *   Faturamento (Billing) com base no tempo de permanência.
    *   Notificações.
    *   **Fluxo de Saída de Veículos e Pagamento (Refatorado):** A saída de veículos agora é um processo de múltiplas etapas, garantindo que a finalização da saída ocorra somente após a confirmação de pagamento. Este fluxo envolve a iniciação da saída, a criação de um registro de faturamento, uma requisição de pagamento externa e, por fim, a confirmação da saída.
*   **Detalhes:** O sistema é projetado para ser robusto, manutenível e escalável, seguindo princípios de design modernos.

## 2. Arquitetura e Design de Software

*   **Padrão Arquitetural:** **Monólito Modular**. O sistema é uma aplicação única, mas o código é organizado em módulos de negócio independentes (ex: `entry`, `billing`, `notification`), cada um com suas próprias responsabilidades. A comunicação entre módulos é predominantemente assíncrona, orquestrada via eventos.
*   **Padrão de Design:** **Domain-Driven Design (DDD)**.
    *   **Camadas:** Cada módulo deve ser estritamente separado nas seguintes camadas:
        1.  `domain`: Contém a lógica de negócio principal.
            *   `model`: Entidades, Agregados e Objetos de Valor (Domínio Rico). A lógica deve residir aqui.
            *   `repository`: Interfaces para persistência.
        2.  `application`: Orquestra os casos de uso do negócio. Contém as classes de `UseCase` que executam a lógica de domínio. Esta camada não deve ter conhecimento sobre a web ou outros detalhes de infraestrutura. Também gerencia a publicação e escuta de eventos de domínio para coordenação entre módulos, incluindo a orquestração de **sagas** para processos de negócio complexos, como o fluxo de saída e pagamento.
        3.  `infrastructure`: Contém os detalhes técnicos.
            *   `web` ou `rest`: Controllers, DTOs e Mappers.
            *   `persistence`: Implementações das interfaces de repositório (ex: usando Spring Data JPA).
    *   **Comunicação entre Módulos:** A comunicação deve ser feita de forma assíncrona através de **eventos de domínio** para manter o baixo acoplamento entre os módulos. Exemplos incluem `VehicleExitedEvent`, `VehicleExitInitiatedEvent`, `PaymentSuccessfulEvent`. **Sagas e coordenação de processos de negócio** são gerenciados através da publicação e escuta de múltiplos eventos em sequência, como exemplificado pelo fluxo de saída e pagamento, onde a confirmação da saída depende de um evento de pagamento bem-sucedido.

## 3. Linguagens e Dependências

*   **Linguagem Principal:** **Java**.
*   **Framework Principal:** **Spring Boot**.
*   **Build Tool:** **Maven**. As dependências e o ciclo de vida do projeto são gerenciados através do `pom.xml`.
*   **Padrões de Dependências:**
    *   **Persistência:** **Spring Data JPA** / **Hibernate**. As entidades são anotadas com `jakarta.persistence`.
    *   **Redução de Boilerplate:** **Lombok**. Utilize anotações como `@RequiredArgsConstructor`, `@Getter`, `@Entity`, etc., para manter o código limpo.
    *   **Mapeamento (DTO <=> Domínio):** O padrão é usar **MapStruct**. Crie uma interface `Mapper` na camada de `infrastructure.web` para lidar com toda a conversão entre DTOs e entidades de domínio. A lógica de negócio (`application` e `domain`) **NÃO** deve conhecer DTOs.

## 4. Padrões do Projeto

*   **Padrão de Commit:** **Conventional Commits**. As mensagens devem seguir o formato `type(scope): message`.
    *   **Exemplos:**
        *   `feat(billing): add endpoint to retrieve billing history`
        *   `fix(entry): correct parking fee calculation logic`
        *   `refactor(entry): align application layer with DDD principles`
*   **Estrutura do Código:** Organize o código por funcionalidade dentro de cada módulo, seguindo a estrutura de camadas do DDD.
    *   **Módulo `allocation`:**
        *   `application/dto`: `SlotCreatedResponse`, `SlotCreateRequest`.
        *   `application/mappers`: `SlotMapper`.
        *   `application/usecases`: `CreateSlotUseCase`, `ListSlotUseCase`, `SlotAvailableUseCase`, `SlotOccupationUseCase`.
        *   `domain`: `Slot`.
        *   `infrastructure/percistence`: `SlotRepository`.
        *   `infrastructure/web`: `ListSlotController`, `SlotCreationController`.
    *   **Módulo `billing`:**
        *   `application/dto`: `PaymentRequest`.
        *   `application/usecases`: `BillingService` (agora focado na criação inicial do `BillingRecord` e não publica o evento de sucesso de pagamento), `PaymentUseCase` (responsável por processar o pagamento e publicar `PaymentSuccessfulEvent`).
        *   `domain`: `BillingRecord` (agora inclui `parkingEntryId` para ligar ao registro de entrada e tem métodos para `markAsPaid`).
        *   `infrastructure/percistence`: `BillingRepository`.
        *   `infrastructure/web`: `PaymentController` (expõe endpoint HTTP para processamento de pagamento).
    *   **Módulo `common`:**
        *   `exception`: Classes para exceções customizadas.
        *   `handlers`: `GlobalExceptionHandler` para tratamento centralizado de exceções.
    *   **Módulo `entry`:**
        *   `application/dto`: `ParkingEntryRequest`, `ParkingEntryResponse`, `ParkingExitRequest`.
        *   `application/mappers`: `VehicleEntryMapper`.
        *   `application/usecases`: `ListVehicleEntryUseCase`, `VehicleEntryUseCase`, `VehicleExitUseCase` (refatorado para `initiateVehicleExit` - inicia o processo de saída, e `finalizeVehicleExit` - finaliza a saída após pagamento).
        *   `domain`: `ParkingEntry`.
        *   `infrastructure/percistence`: `ParkingEntryRepository`.
        *   `infrastructure/web`: `ListVehicleEntryController`, `VehicleEntryController`, `VehicleExitController`.
    *   **Módulo `event`:**
        *   Contém os registros de eventos de domínio: `PaymentSuccessfulEvent`, `VehicleEnteredEvent`, `VehicleExitedEvent`, `VehicleExitInitiatedEvent`.
    *   **Módulo `notification`:**
        *   `NotificationService` (ouve eventos relevantes e foi ajustado para `PaymentSuccessfulEvent`).

## 5. Estilo de Interação Esperado

*   **Tom:** Profissional, claro e direto.
*   **Nível de Detalhe:** As respostas devem ser **explicativas**. Sempre justifique as decisões de código, conectando-as aos princípios de arquitetura e design descritos neste guia (DDD, Monólito Modular, etc.).
*   **Processo:** Ao receber uma solicitação, primeiro entenda o contexto, planeje os passos, execute-os e, ao final, forneça uma explicação detalhada sobre "o que" foi feito e "por que" foi feito dessa maneira.
