package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListVehicleEntryUseCase {

    private final ParkingEntryRepository repository;

    @Transactional(readOnly = true)
    public Page<ParkingEntry> getAllEntries(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ParkingEntry> getActives(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public ParkingEntry findByVehicleNumber(
            @NotNull(message = "Vehicle number cannot be null.")
            @Size(min = 4, max = 7, message = "Vehicle number must be 7 digits long.")
            String vehicleNumber
    ) {
        return repository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new EntityNotFoundException("Active vehicle not found"));
    }
}
