package com.example.carparking.billing.infrastructure.web;

import com.example.carparking.billing.application.dto.PaymentRequest;
import com.example.carparking.billing.application.dto.PaymentResponse;
import com.example.carparking.billing.application.mappers.BillingMapper;
import com.example.carparking.billing.application.usecases.ProcessBillingPaymentUseCase;
import com.example.carparking.billing.domain.BillingRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de contrato da API de processamento de pagamentos.
 * Valida a recepção do pedido de pagamento e a resposta com o status pago.
 */
@WebMvcTest(ProcessPaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProcessPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProcessBillingPaymentUseCase processBillingPaymentUseCase;

    @MockitoBean
    private BillingMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    @DisplayName("Deve processar o pagamento com sucesso via POST")
    void processPayment_success() throws Exception {
        // Arrange
        PaymentRequest request = new PaymentRequest(1L);
        BillingRecord billingRecord = BillingRecord.create(1L, "ABC-1234", LocalDateTime.now().minusHours(1), LocalDateTime.now());
        PaymentResponse response = new PaymentResponse(1L, 1L, "ABC-1234", 20.0, Duration.ofHours(1), true, LocalDateTime.now());

        when(processBillingPaymentUseCase.processPayment(any(PaymentRequest.class))).thenReturn(billingRecord);
        when(mapper.toDto(any(BillingRecord.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/billing/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid").value(true))
                .andExpect(jsonPath("$.vehicleNumber").value("ABC-1234"));
    }
}
