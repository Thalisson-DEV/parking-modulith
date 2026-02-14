package com.example.carparking.billing;

import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository repository;

    @EventListener
    public void handleVehicleExit(VehicleExitedEvent event) {
        BillingRecord record = new BillingRecord();
        record.setVehicleNumber(event.vehicleNumber());
        record.setBillingTime(event.exitTime());
        record.setAmount(event.entryTime(), event.exitTime());
        repository.save(record);
    }
}
