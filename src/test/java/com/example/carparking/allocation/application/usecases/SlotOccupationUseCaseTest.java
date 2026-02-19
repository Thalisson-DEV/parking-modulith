package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlotOccupationUseCaseTest {

    @Mock
    private SlotRepository repository;

    @InjectMocks
    private SlotOccupationUseCase useCase;

    @Test
    @DisplayName("Deve ocupar a primeira vaga disponível com sucesso")
    void handleVehicleEntry_success() {
        // Arrange: Prepara uma vaga disponível e o evento de entrada
        String vehicleNumber = "ABC-1234";
        VehicleEnteredEvent event = new VehicleEnteredEvent(vehicleNumber, LocalDateTime.now());
        Slot slot = Slot.create("A1");
        
        when(repository.findFirstByAvailableTrue()).thenReturn(Optional.of(slot));

        // Act: Executa a ocupação da vaga
        useCase.handleVehicleEntry(event);

        // Assert: Verifica se a vaga foi ocupada pelo veículo correto
        assertFalse(slot.isAvailable());
        assertEquals(vehicleNumber, slot.getVehicleNumber());
        verify(repository, times(1)).save(slot);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver vagas disponíveis")
    void handleVehicleEntry_noAvailableSlot() {
        // Arrange: Simula estacionamento lotado
        VehicleEnteredEvent event = new VehicleEnteredEvent("ABC-1234", LocalDateTime.now());
        when(repository.findFirstByAvailableTrue()).thenReturn(Optional.empty());

        // Act & Assert: Verifica se o erro de falta de vagas é disparado
        assertThrows(EntityNotFoundException.class, () -> useCase.handleVehicleEntry(event));
        verify(repository, never()).save(any());
    }
}
