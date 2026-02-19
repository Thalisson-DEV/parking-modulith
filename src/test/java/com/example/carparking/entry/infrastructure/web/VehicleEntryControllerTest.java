package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.dto.ParkingEntryRequest;
import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.VehicleEntryUseCase;
import com.example.carparking.entry.domain.ParkingEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de contrato da API de entrada de veículos.
 * Valida a criação do registro e o cabeçalho Location.
 */
@WebMvcTest(VehicleEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
class VehicleEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleEntryUseCase vehicleEntryUseCase;

    @MockitoBean
    private VehicleEntryMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    @DisplayName("Deve registrar a entrada do veículo e retornar 201 Created")
    void vehicleEntry_success() throws Exception {
        // Arrange
        ParkingEntryRequest request = new ParkingEntryRequest("ABC-1234");
        ParkingEntry entry = ParkingEntry.create("ABC-1234");
        ParkingEntryResponse response = new ParkingEntryResponse(1L, "ABC-1234", LocalDateTime.now(), null);

        when(vehicleEntryUseCase.vehicleEntry(anyString())).thenReturn(entry);
        when(mapper.toDto(any(ParkingEntry.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/parking/entry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/parking/1"))
                .andExpect(jsonPath("$.vehicleNumber").value("ABC-1234"));
    }
}
