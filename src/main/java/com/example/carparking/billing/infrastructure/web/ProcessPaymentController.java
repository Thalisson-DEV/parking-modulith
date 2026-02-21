package com.example.carparking.billing.infrastructure.web;

import com.example.carparking.billing.doc.ProcessPaymentControllerDocs;
import com.example.carparking.billing.application.dto.PaymentRequest;
import com.example.carparking.billing.application.dto.PaymentResponse;
import com.example.carparking.billing.application.mappers.BillingMapper;
import com.example.carparking.billing.application.usecases.ProcessBillingPaymentUseCase;
import com.example.carparking.billing.domain.BillingRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProcessPaymentController implements ProcessPaymentControllerDocs {

    private final ProcessBillingPaymentUseCase processBillingPaymentUseCase;
    private final BillingMapper mapper;

    @PostMapping("/billing/payment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody @Valid PaymentRequest request) {
        BillingRecord processedBilling = processBillingPaymentUseCase.processPayment(request);
        PaymentResponse response = mapper.toDto(processedBilling);
        return ResponseEntity.ok(response);
    }
}
