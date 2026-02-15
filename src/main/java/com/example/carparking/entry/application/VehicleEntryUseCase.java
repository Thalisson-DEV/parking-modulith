package com.example.carparking.entry.application;

import com.example.carparking.entry.domain.model.ParkingEntry;
import com.example.carparking.entry.domain.repository.ParkingEntryRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleEntryUseCase {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public ParkingEntry vehicleEntry(String vehicleNumber) {
        ParkingEntry newEntry = ParkingEntry.create(vehicleNumber);
        ParkingEntry savedEntry = repository.save(newEntry);

        publisher.publishEvent(new VehicleEnteredEvent(savedEntry.getVehicleNumber(), savedEntry.getEntryTime()));

        return savedEntry;
    }

    @Transactional(readOnly = true)
    public Page<ParkingEntry> getAllEntries(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ParkingEntry> getActives(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }
}
