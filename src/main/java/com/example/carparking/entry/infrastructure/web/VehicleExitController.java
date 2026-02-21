package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.application.dto.ParkingExitRequest;
import com.example.carparking.entry.application.mappers.VehicleEntryMapper;
import com.example.carparking.entry.application.usecases.VehicleExitUseCase;
import com.example.carparking.entry.doc.VehicleExitControllerDocs;
import com.example.carparking.entry.domain.ParkingEntry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleExitController implements VehicleExitControllerDocs {

    private final VehicleExitUseCase vehicleExitUseCase;
    private final VehicleEntryMapper mapper;

    @PostMapping("/parking/entry/exit")
    public ResponseEntity<ParkingEntryResponse> vehicleExit(@RequestBody @Valid ParkingExitRequest request) {
        ParkingEntry exitedEntry = vehicleExitUseCase.initiateVehicleExit(request.vehicleNumber());
        ParkingEntryResponse response = mapper.toDto(exitedEntry);
        return ResponseEntity.ok(response);
    }
}
