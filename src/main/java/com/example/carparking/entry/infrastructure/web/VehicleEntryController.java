package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.VehicleEntryUseCase;
import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.application.dto.ParkingEntryRequest;
import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleEntryController {

    private final VehicleEntryUseCase vehicleEntryUseCase;
    private final VehicleEntryMapper mapper;

    @PostMapping("/parking/entry")
    public ResponseEntity<ParkingEntryResponse> vehicleEntry(@RequestBody @Valid ParkingEntryRequest request) {
        ParkingEntry newEntry = vehicleEntryUseCase.vehicleEntry(request.vehicleNumber());
        ParkingEntryResponse response = mapper.toDto(newEntry);
        return ResponseEntity.created(URI.create("/api/v1/parking/" + response.id())).body(response);
    }
}
