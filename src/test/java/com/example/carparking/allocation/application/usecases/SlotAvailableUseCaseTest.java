package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import com.example.carparking.event.VehicleExitedEvent;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SlotAvailableUseCaseTest {

    @Mock
    private SlotRepository repository;

    @InjectMocks
    private SlotAvailableUseCase useCase;

    @Test
    @DisplayName("Deve liberar uma vaga com sucesso quando o veículo sai")
    void handlerVehicleExit_success() {
        // Arrange: Prepara o cenário com um veículo e uma vaga ocupada
        String vehicleNumber = "ABC-1234";
        LocalDateTime exitTime = LocalDateTime.now().plusHours(1);
        VehicleExitedEvent event = new VehicleExitedEvent(vehicleNumber, LocalDateTime.now(), exitTime);
        Slot slot = Slot.create("A1");
        slot.occupationSlot(vehicleNumber);
        
        when(repository.findByVehicleNumber(vehicleNumber)).thenReturn(Optional.of(slot));

        // Act: Executa a ação de liberar a vaga
        useCase.handlerVehicleExit(event);

        // Assert: Verifica se a vaga ficou disponível e se foi salva
        assertTrue(slot.isAvailable());
        verify(repository, times(1)).save(slot);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o veículo não é encontrado em nenhuma vaga")
    void handlerVehicleExit_notFound() {
        // Arrange: Evento com veículo inexistente no registro de vagas
        String vehicleNumber = "XYZ-0000";
        LocalDateTime exitTime = LocalDateTime.now().plusHours(1);
        VehicleExitedEvent event = new VehicleExitedEvent(vehicleNumber, LocalDateTime.now(), exitTime);
        when(repository.findByVehicleNumber(vehicleNumber)).thenReturn(Optional.empty());

        // Act & Assert: Verifica se a exceção correta é lançada
        assertThrows(EntityNotFoundException.class, () -> useCase.handlerVehicleExit(event));
        verify(repository, never()).save(any());
    }
}
