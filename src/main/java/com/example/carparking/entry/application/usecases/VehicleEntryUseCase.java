package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleEntryUseCase {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public ParkingEntry vehicleEntry(@Valid String vehicleNumber) {
        if (repository.existsByVehicleNumberAndActiveTrue(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle has a entry already registered.");
        }
        ParkingEntry newEntry = ParkingEntry.create(vehicleNumber);
        ParkingEntry savedEntry = repository.save(newEntry);

        publisher.publishEvent(new VehicleEnteredEvent(savedEntry.getVehicleNumber(), savedEntry.getEntryTime()));

        return savedEntry;
    }


}
