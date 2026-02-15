package com.example.carparking.entry.application;

import com.example.carparking.entry.api.ParkingEntryMapper;
import com.example.carparking.entry.api.dto.ParkingEntryResponse;
import com.example.carparking.entry.api.dto.ParkingExitRequest;
import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.domain.ParkingEntryRepository;
import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExitService {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;
    private final ParkingEntryMapper mapper;

    @Transactional
    public ParkingEntryResponse vehicleExit(ParkingExitRequest request) {
        ParkingEntry entry = repository.findByVehicleNumberAndActiveTrue(request.vehicleNumber())
                .orElseThrow(() -> new IllegalArgumentException("Active vehicle not found"));
        
        entry.recordExit();
        
        ParkingEntry savedEntry = repository.save(entry);

        publisher.publishEvent(new VehicleExitedEvent(savedEntry.getVehicleNumber(), savedEntry.getEntryTime(), savedEntry.getExitTime()));

        return mapper.toDto(savedEntry);
    }
}
