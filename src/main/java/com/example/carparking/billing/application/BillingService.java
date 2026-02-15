package com.example.carparking.billing;

import com.example.carparking.billing.domain.BillingRecord;
import com.example.carparking.billing.domain.BillingRepository;
import com.example.carparking.event.PaymentSuccesfulEvent;
import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository repository;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void handleVehicleExit(VehicleExitedEvent event) {
        BillingRecord billing = BillingRecord.create(event.vehicleNumber(), event.entryTime(), event.exitTime());
        repository.save(billing);

        publisher.publishEvent(new PaymentSuccesfulEvent(billing.getVehicleNumber(), billing.getBillingTime(), billing.getAmount()));
    }
}
