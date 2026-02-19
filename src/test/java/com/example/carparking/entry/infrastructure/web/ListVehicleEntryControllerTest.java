package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.ListVehicleEntryUseCase;
import com.example.carparking.entry.domain.ParkingEntry;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de contrato da API de listagem de registros de estacionamento.
 */
@WebMvcTest(ListVehicleEntryController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListVehicleEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ListVehicleEntryUseCase listVehicleEntryUseCase;

    @MockitoBean
    private VehicleEntryMapper mapper;

    @Test
    @DisplayName("Deve retornar listagem de registros via GET")
    void getAllEntries_success() throws Exception {
        Page<ParkingEntry> page = new PageImpl<>(Collections.singletonList(ParkingEntry.create("ABC-1234")));
        ParkingEntryResponse response = new ParkingEntryResponse(1L, "ABC-1234", LocalDateTime.now(), null);

        when(listVehicleEntryUseCase.getAllEntries(any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(any(ParkingEntry.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/parking/entry"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vehicleNumber").value("ABC-1234"));
    }

    @Test
    @DisplayName("Deve buscar registro por placa via GET")
    void findByVehicleNumber_success() throws Exception {
        ParkingEntry entry = ParkingEntry.create("ABC-1234");
        ParkingEntryResponse response = new ParkingEntryResponse(1L, "ABC-1234", LocalDateTime.now(), null);

        when(listVehicleEntryUseCase.findByVehicleNumber(anyString())).thenReturn(entry);
        when(mapper.toDto(any(ParkingEntry.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/parking/entry/vehicle-number/ABC-1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value("ABC-1234"));
    }
}
