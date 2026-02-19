package com.example.carparking.billing.application.usecases;

import com.example.carparking.billing.application.dto.PaymentRequest;
import com.example.carparking.billing.domain.BillingRecord;
import com.example.carparking.billing.infrastructure.percistence.BillingRepository;
import com.example.carparking.event.PaymentSuccessfulEvent;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessBillingPaymentUseCaseTest {

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private ProcessBillingPaymentUseCase useCase;

    @Test
    @DisplayName("Deve processar pagamento com sucesso e publicar evento")
    void processPayment_success() {
        // Arrange
        Long billingId = 1L;
        PaymentRequest request = new PaymentRequest(billingId);
        BillingRecord billingRecord = BillingRecord.create(1L, "ABC-1234", LocalDateTime.now().minusHours(1), LocalDateTime.now());
        
        when(billingRepository.findById(billingId)).thenReturn(Optional.of(billingRecord));
        when(billingRepository.save(any(BillingRecord.class))).thenReturn(billingRecord);

        // Act
        BillingRecord result = useCase.processPayment(request);

        // Assert
        assertTrue(result.isPaid());
        assertNotNull(result.getPaymentTime());
        verify(billingRepository, times(1)).save(billingRecord);
        verify(publisher, times(1)).publishEvent(any(PaymentSuccessfulEvent.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando registro de faturamento não for encontrado")
    void processPayment_notFound() {
        // Arrange
        PaymentRequest request = new PaymentRequest(1L);
        when(billingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> useCase.processPayment(request));
        verify(publisher, never()).publishEvent(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar pagar um registro já pago")
    void processPayment_alreadyPaid() {
        // Arrange
        Long billingId = 1L;
        PaymentRequest request = new PaymentRequest(billingId);
        BillingRecord billingRecord = BillingRecord.create(1L, "ABC-1234", LocalDateTime.now().minusHours(1), LocalDateTime.now());
        billingRecord.markAsPaid(); // Já pago
        
        when(billingRepository.findById(billingId)).thenReturn(Optional.of(billingRecord));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> useCase.processPayment(request));
    }
}
