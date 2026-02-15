package com.example.carparking.entry.application;

import com.example.carparking.entry.domain.model.ParkingEntry;
import com.example.carparking.entry.domain.repository.ParkingEntryRepository;
import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleExitUseCase {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public ParkingEntry vehicleExit(String vehicleNumber) {
        ParkingEntry entry = repository.findByVehicleNumberAndActiveTrue(vehicleNumber)
                .orElseThrow(() -> new IllegalArgumentException("Active vehicle not found"));
        
        entry.recordExit();
        
        ParkingEntry savedEntry = repository.save(entry);

        publisher.publishEvent(new VehicleExitedEvent(savedEntry.getVehicleNumber(), savedEntry.getEntryTime(), savedEntry.getExitTime()));

        return savedEntry;
    }
}
