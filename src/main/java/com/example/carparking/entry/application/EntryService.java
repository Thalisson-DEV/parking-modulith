package com.example.carparking.entry.application;

import com.example.carparking.entry.api.ParkingEntryMapper;
import com.example.carparking.entry.api.dto.ParkingEntryRequest;
import com.example.carparking.entry.api.dto.ParkingEntryResponse;
import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.domain.ParkingEntryRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;
    private final ParkingEntryMapper mapper;

    @Transactional
    public ParkingEntryResponse vehicleEntry(ParkingEntryRequest request) {
        ParkingEntry newEntry = ParkingEntry.create(request.vehicleNumber());
        ParkingEntry savedEntry = repository.save(newEntry);
        publisher.publishEvent(new VehicleEnteredEvent(savedEntry.getVehicleNumber(), savedEntry.getEntryTime()));

        return mapper.toDto(savedEntry);
    }

    @Transactional(readOnly = true)
    public Page<ParkingEntryResponse> getAllEntries(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ParkingEntryResponse> getActives(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable).map(mapper::toDto);
    }
}
