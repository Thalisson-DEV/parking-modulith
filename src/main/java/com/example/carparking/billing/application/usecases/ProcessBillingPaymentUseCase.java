package com.example.carparking.billing.application.usecases;

import com.example.carparking.billing.application.dto.PaymentRequest;
import com.example.carparking.billing.domain.BillingRecord;
import com.example.carparking.billing.infrastructure.percistence.BillingRepository;
import com.example.carparking.event.PaymentSuccessfulEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProcessBillingPaymentUseCase {

    private final BillingRepository billingRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public BillingRecord processPayment(PaymentRequest request) {
        BillingRecord billingRecord = billingRepository.findById(request.billingRecordId())
                .orElseThrow(() -> new EntityNotFoundException("Billing record not found for ID: " + request.billingRecordId()));

        billingRecord.markAsPaid();
        BillingRecord savedBillingRecord = billingRepository.save(billingRecord);

        publisher.publishEvent(new PaymentSuccessfulEvent(
                savedBillingRecord.getParkingEntryId(),
                savedBillingRecord.getVehicleNumber(),
                savedBillingRecord.getAmount(),
                savedBillingRecord.getId()
        ));
        return savedBillingRecord;
    }
}
