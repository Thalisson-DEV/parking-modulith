package com.example.carparking.billing.application.usecases;

import com.example.carparking.billing.domain.BillingRecord;
import com.example.carparking.billing.infrastructure.percistence.BillingRepository;
import com.example.carparking.event.VehicleExitInitiatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateBillingUseCase {

    private final BillingRepository repository;

    @EventListener
    @Transactional
    public BillingRecord handleVehicleExitInitiatedEvent(VehicleExitInitiatedEvent event) {
        LocalDateTime exitInitiatedTime = LocalDateTime.now();

        BillingRecord billing = BillingRecord.create(
                event.parkingEntryId(),
                event.vehicleNumber(),
                event.entryTime(),
                exitInitiatedTime
        );
        return repository.save(billing);
    }
}
