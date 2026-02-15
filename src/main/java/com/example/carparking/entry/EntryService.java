package com.example.carparking.entry;

import com.example.carparking.entry.dto.ParkingEntryMapper;
import com.example.carparking.entry.dto.ParkingEntryRequest;
import com.example.carparking.entry.dto.ParkingEntryResponse;
import com.example.carparking.event.VehicleEnteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;
    private final ParkingEntryMapper mapper;

    @Transactional
    public ParkingEntryResponse vehicleEntry(ParkingEntryRequest request) {
        ParkingEntry entry = mapper.toEntity(request);
        entry.setEntryTime(LocalDateTime.now());
        entry.setActive(true);
        ParkingEntry savedEntry = repository.save(entry);
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
