package com.example.carparking.notification;

import com.example.carparking.event.PaymentSuccessfulEvent;
import com.example.carparking.event.VehicleEnteredEvent;
import com.example.carparking.event.VehicleExitedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("Deve processar notificação de entrada de veículo")
    void notifyOnVehicleEntry_success() {
        VehicleEnteredEvent event = new VehicleEnteredEvent("ABC-1234", LocalDateTime.now());
        assertDoesNotThrow(() -> notificationService.notifyOnVehicleEntry(event));
    }

    @Test
    @DisplayName("Deve processar notificação de saída de veículo")
    void notifyOnVehicleExit_success() {
        VehicleExitedEvent event = new VehicleExitedEvent("ABC-1234", LocalDateTime.now().minusHours(1), LocalDateTime.now());
        assertDoesNotThrow(() -> notificationService.notifyOnVehicleExit(event));
    }

    @Test
    @DisplayName("Deve processar notificação de pagamento bem-sucedido")
    void notifyOnPaymentSuccessful_success() {
        PaymentSuccessfulEvent event = new PaymentSuccessfulEvent(1L, "ABC-1234", 20.0, 100L);
        assertDoesNotThrow(() -> notificationService.notifyOnPaymentSuccessful(event));
    }
}
