package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListVehicleEntryUseCaseTest {

    @Mock
    private ParkingEntryRepository repository;

    @InjectMocks
    private ListVehicleEntryUseCase useCase;

    @Test
    @DisplayName("Deve retornar página de entradas")
    void getAllEntries_success() {
        Pageable pageable = Pageable.unpaged();
        Page<ParkingEntry> page = new PageImpl<>(Collections.singletonList(ParkingEntry.create("ABC-1234")));
        when(repository.findAll(pageable)).thenReturn(page);

        Page<ParkingEntry> result = useCase.getAllEntries(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("Deve retornar apenas entradas ativas")
    void getActives_success() {
        Pageable pageable = Pageable.unpaged();
        Page<ParkingEntry> page = new PageImpl<>(Collections.singletonList(ParkingEntry.create("ABC-1234")));
        when(repository.findAllByActiveTrue(pageable)).thenReturn(page);

        Page<ParkingEntry> result = useCase.getActives(pageable);

        assertNotNull(result);
        assertTrue(result.getContent().get(0).isActive());
    }

    @Test
    @DisplayName("Deve buscar entrada por placa com sucesso")
    void findByVehicleNumber_success() {
        String plate = "ABC-1234";
        ParkingEntry entry = ParkingEntry.create(plate);
        when(repository.findByVehicleNumber(plate)).thenReturn(Optional.of(entry));

        ParkingEntry result = useCase.findByVehicleNumber(plate);

        assertNotNull(result);
        assertEquals(plate, result.getVehicleNumber());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar placa inexistente")
    void findByVehicleNumber_notFound() {
        when(repository.findByVehicleNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> useCase.findByVehicleNumber("XYZ-0000"));
    }
}
