package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleEntryUseCaseTest {

    @Mock
    private ParkingEntryRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private VehicleEntryUseCase useCase;

    @Test
    @DisplayName("Deve registrar entrada de veículo com sucesso")
    void vehicleEntry_success() {
        // Arrange
        String vehicleNumber = "ABC-1234";
        ParkingEntry entry = ParkingEntry.create(vehicleNumber);
        when(repository.existsByVehicleNumberAndActiveTrue(vehicleNumber)).thenReturn(false);
        when(repository.save(any(ParkingEntry.class))).thenReturn(entry);

        // Act
        ParkingEntry result = useCase.vehicleEntry(vehicleNumber);

        // Assert
        assertNotNull(result);
        assertEquals(vehicleNumber, result.getVehicleNumber());
        verify(repository, times(1)).save(any(ParkingEntry.class));
        verify(publisher, times(1)).publishEvent(any(VehicleEnteredEvent.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar registrar entrada de veículo já estacionado")
    void vehicleEntry_alreadyRegistered() {
        // Arrange
        String vehicleNumber = "ABC-1234";
        when(repository.existsByVehicleNumberAndActiveTrue(vehicleNumber)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> useCase.vehicleEntry(vehicleNumber));
        verify(repository, never()).save(any());
    }
}
