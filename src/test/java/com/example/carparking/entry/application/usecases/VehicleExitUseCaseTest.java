package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import com.example.carparking.event.PaymentSuccessfulEvent;
import com.example.carparking.event.VehicleExitInitiatedEvent;
import com.example.carparking.event.VehicleExitedEvent;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleExitUseCaseTest {

    @Mock
    private ParkingEntryRepository repository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private VehicleExitUseCase useCase;

    @Test
    @DisplayName("Deve iniciar a saída do veículo com sucesso")
    void initiateVehicleExit_success() {
        // Arrange
        String vehicleNumber = "ABC-1234";
        ParkingEntry entry = ParkingEntry.create(vehicleNumber);
        when(repository.findByVehicleNumberAndActiveTrue(vehicleNumber)).thenReturn(Optional.of(entry));

        // Act
        ParkingEntry result = useCase.initiateVehicleExit(vehicleNumber);

        // Assert
        assertNotNull(result);
        verify(publisher, times(1)).publishEvent(any(VehicleExitInitiatedEvent.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao iniciar saída de veículo não encontrado")
    void initiateVehicleExit_notFound() {
        // Arrange
        String vehicleNumber = "ABC-1234";
        when(repository.findByVehicleNumberAndActiveTrue(vehicleNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> useCase.initiateVehicleExit(vehicleNumber));
    }

    @Test
    @DisplayName("Deve finalizar a saída do veículo após pagamento")
    void finalizeVehicleExit_success() {
        // Arrange
        PaymentSuccessfulEvent paymentEvent = new PaymentSuccessfulEvent(1L, "ABC-1234", 20.0, 100L);
        ParkingEntry entry = ParkingEntry.create("ABC-1234");
        when(repository.findById(1L)).thenReturn(Optional.of(entry));

        // Act
        useCase.finalizeVehicleExit(paymentEvent);

        // Assert
        assertFalse(entry.isActive());
        assertNotNull(entry.getExitTime());
        verify(repository, times(1)).save(entry);
        verify(publisher, times(1)).publishEvent(any(VehicleExitedEvent.class));
    }
}
