package com.example.carparking.allocation.infrastructure.web;

import com.example.carparking.allocation.application.dto.SlotCreateRequest;
import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import com.example.carparking.allocation.application.mappers.SlotMapper;
import com.example.carparking.allocation.application.usecases.CreateSlotUseCase;
import com.example.carparking.allocation.domain.Slot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de contrato da API de criação de vagas.
 * Valida o mapeamento HTTP, tratamento de JSON e retorno de status.
 */
@WebMvcTest(SlotCreationController.class)
@AutoConfigureMockMvc(addFilters = false)
class SlotCreationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateSlotUseCase createSlotUseCase;

    @MockitoBean
    private SlotMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    @DisplayName("Deve retornar HTTP 200 (OK) e os dados da vaga ao criar com sucesso")
    void createSlot_success() throws Exception {
        // Arrange: Mock dos dados de entrada e saída
        SlotCreateRequest request = new SlotCreateRequest("A1");
        Slot slot = Slot.create("A1");
        SlotCreatedResponse response = new SlotCreatedResponse(1L, "A1", true);

        when(createSlotUseCase.createSlot(anyString())).thenReturn(slot);
        when(mapper.toDto(any(Slot.class))).thenReturn(response);

        // Act & Assert: Simula a requisição POST e valida o JSON de retorno
        mockMvc.perform(post("/api/v1/slot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotCode").value("A1"))
                .andExpect(jsonPath("$.available").value(true));
    }
}
