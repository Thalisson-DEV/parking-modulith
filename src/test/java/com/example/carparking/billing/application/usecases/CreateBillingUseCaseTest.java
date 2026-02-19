package com.example.carparking.billing.application.usecases;

import com.example.carparking.billing.domain.BillingRecord;
import com.example.carparking.billing.infrastructure.percistence.BillingRepository;
import com.example.carparking.event.VehicleExitInitiatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBillingUseCaseTest {

    @Mock
    private BillingRepository repository;

    @InjectMocks
    private CreateBillingUseCase useCase;

    @Test
    @DisplayName("Deve criar um registro de faturamento ao receber evento de saída iniciada")
    void handleVehicleExitInitiatedEvent_success() {
        // Arrange
        Long parkingEntryId = 1L;
        String vehicleNumber = "ABC-1234";
        LocalDateTime entryTime = LocalDateTime.now().minusHours(2);
        VehicleExitInitiatedEvent event = new VehicleExitInitiatedEvent(parkingEntryId, vehicleNumber, entryTime);

        when(repository.save(any(BillingRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BillingRecord result = useCase.handleVehicleExitInitiatedEvent(event);

        // Assert
        assertNotNull(result);
        assertEquals(vehicleNumber, result.getVehicleNumber());
        assertEquals(parkingEntryId, result.getParkingEntryId());
        assertFalse(result.isPaid());
        assertTrue(result.getAmount() >= 20.0); // Valor mínimo
        verify(repository, times(1)).save(any(BillingRecord.class));
    }
}
