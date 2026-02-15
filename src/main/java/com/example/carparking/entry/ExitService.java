package com.example.carparking.entry;

import com.example.carparking.entry.dto.ParkingEntryMapper;
import com.example.carparking.entry.dto.ParkingEntryResponse;
import com.example.carparking.entry.dto.ParkingExitRequest;
import com.example.carparking.event.VehicleExitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
