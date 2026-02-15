package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.VehicleEntryUseCase;
import com.example.carparking.entry.application.VehicleExitUseCase;
import com.example.carparking.entry.domain.model.ParkingEntry;
import com.example.carparking.entry.infrastructure.web.dto.ParkingEntryRequest;
import com.example.carparking.entry.infrastructure.web.dto.ParkingEntryResponse;
import com.example.carparking.entry.infrastructure.web.dto.ParkingExitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parking")
@RequiredArgsConstructor
public class EntryController {

    private final VehicleEntryUseCase vehicleEntryUseCase;
    private final VehicleExitUseCase vehicleExitUseCase;
    private final ParkingEntryMapper mapper;

    @PostMapping("/entry")
    public ResponseEntity<ParkingEntryResponse> vehicleEntry(@RequestBody @Valid ParkingEntryRequest request) {
        ParkingEntry newEntry = vehicleEntryUseCase.vehicleEntry(request.vehicleNumber());
        ParkingEntryResponse response = mapper.toDto(newEntry);
        return ResponseEntity.created(URI.create("/api/v1/parking/" + response.id())).body(response);
    }

    @PostMapping("/exit")
    public ResponseEntity<ParkingEntryResponse> vehicleExit(@RequestBody @Valid ParkingExitRequest request) {
        ParkingEntry exitedEntry = vehicleExitUseCase.vehicleExit(request.vehicleNumber());
        ParkingEntryResponse response = mapper.toDto(exitedEntry);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ParkingEntryResponse>> getAllEntries(Pageable pageable) {
        Page<ParkingEntry> page = vehicleEntryUseCase.getAllEntries(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/actives")
    public ResponseEntity<Page<ParkingEntryResponse>> getActives(Pageable pageable) {
        Page<ParkingEntry> page = vehicleEntryUseCase.getActives(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }
}
