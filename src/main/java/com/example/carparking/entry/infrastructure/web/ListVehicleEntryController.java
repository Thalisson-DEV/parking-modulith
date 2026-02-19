package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.ListVehicleEntryUseCase;
import com.example.carparking.entry.domain.ParkingEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ListVehicleEntryController {

    private final ListVehicleEntryUseCase listVehicleEntryUseCase;
    private final VehicleEntryMapper mapper;

    @GetMapping("/parking/entry")
    public ResponseEntity<Page<ParkingEntryResponse>> getAllEntries(Pageable pageable) {
        Page<ParkingEntry> page = listVehicleEntryUseCase.getAllEntries(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/parking/entry/actives")
    public ResponseEntity<Page<ParkingEntryResponse>> getActives(Pageable pageable) {
        Page<ParkingEntry> page = listVehicleEntryUseCase.getActives(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/parking/entry/vehicle-number/{vehicleNumber}")
    public ResponseEntity<ParkingEntryResponse> findByVehicleNumber(@PathVariable("vehicleNumber") @Valid String vehicleNumber) {
        ParkingEntry entry = listVehicleEntryUseCase.findByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(mapper.toDto(entry));
    }
}
