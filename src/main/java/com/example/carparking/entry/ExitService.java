package com.example.carparking.entry;

import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExitService {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    public void vehicleExit(String vehicleNumber) {
        ParkingEntry entry = repository.findByVehicleNumberAndActiveTrue(vehicleNumber)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        entry.setActive(false);
        entry.setExitTime(LocalDateTime.now());
        repository.save(entry);

        publisher.publishEvent(new VehicleExitedEvent(vehicleNumber, entry.getEntryTime(), entry.getExitTime()));
    }
}
