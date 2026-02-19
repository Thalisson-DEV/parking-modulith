package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.application.dto.ParkingExitRequest;
import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.VehicleExitUseCase;
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
 * Testes de contrato da API que inicia o processo de saída.
 */
@WebMvcTest(VehicleExitController.class)
@AutoConfigureMockMvc(addFilters = false)
class VehicleExitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleExitUseCase vehicleExitUseCase;

    @MockitoBean
    private VehicleEntryMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    @DisplayName("Deve iniciar o processo de saída com sucesso e retornar 200 OK")
    void vehicleExit_success() throws Exception {
        // Arrange
        ParkingExitRequest request = new ParkingExitRequest("ABC-1234");
        ParkingEntry entry = ParkingEntry.create("ABC-1234");
        ParkingEntryResponse response = new ParkingEntryResponse(1L, "ABC-1234", LocalDateTime.now(), null);

        when(vehicleExitUseCase.initiateVehicleExit(anyString())).thenReturn(entry);
        when(mapper.toDto(any(ParkingEntry.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/parking/entry/exit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value("ABC-1234"));
    }
}
