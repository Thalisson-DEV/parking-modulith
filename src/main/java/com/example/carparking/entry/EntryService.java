package com.example.carparking.entry;

import com.example.carparking.event.VehicleEnteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    public void vehicleEntry(String vehicleNumber) {
        ParkingEntry entry = new ParkingEntry(null, vehicleNumber, LocalDateTime.now(), null, true);
        repository.save(entry);
        publisher.publishEvent(new VehicleEnteredEvent(vehicleNumber, entry.getEntryTime()));
    }
}
