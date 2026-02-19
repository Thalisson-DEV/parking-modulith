package com.example.carparking.allocation.infrastructure.web;

import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import com.example.carparking.allocation.application.mappers.SlotMapper;
import com.example.carparking.allocation.application.usecases.ListSlotUseCase;
import com.example.carparking.allocation.domain.Slot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de contrato da API de listagem e busca de vagas.
 */
@WebMvcTest(ListSlotController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListSlotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListSlotUseCase listSlotUseCase;

    @MockitoBean
    private SlotMapper mapper;

    @Test
    @DisplayName("Deve retornar página de vagas via GET")
    void getAllSlots_success() throws Exception {
        Slot slot = Slot.create("A1");
        Page<Slot> page = new PageImpl<>(Collections.singletonList(slot));
        SlotCreatedResponse response = new SlotCreatedResponse(1L, "A1", true);

        when(listSlotUseCase.getAllSlots(any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(Slot.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/slot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].slotCode").value("A1"));
    }

    @Test
    @DisplayName("Deve retornar vaga por código via GET")
    void findBySlotCode_success() throws Exception {
        Slot slot = Slot.create("A1");
        SlotCreatedResponse response = new SlotCreatedResponse(1L, "A1", true);

        when(listSlotUseCase.findBySlotCode("A1")).thenReturn(slot);
        when(mapper.toDto(any(Slot.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/slot/slot-code/A1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotCode").value("A1"));
    }
}
