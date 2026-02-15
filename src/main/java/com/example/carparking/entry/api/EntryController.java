package com.example.carparking.entry.api;

import com.example.carparking.entry.api.dto.ParkingEntryRequest;
import com.example.carparking.entry.api.dto.ParkingEntryResponse;
import com.example.carparking.entry.api.dto.ParkingExitRequest;
import com.example.carparking.entry.application.EntryService;
import com.example.carparking.entry.application.ExitService;
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

    private final EntryService entryService;
    private final ExitService exitService;

    @PostMapping("/entry")
    public ResponseEntity<ParkingEntryResponse> vehicleEntry(@RequestBody @Valid ParkingEntryRequest request) {
        ParkingEntryResponse response = entryService.vehicleEntry(request);
        return ResponseEntity.created(URI.create("/api/v1/parking/" + response.id())).body(response);
    }

    @PostMapping("/exit")
    public ResponseEntity<ParkingEntryResponse> vehicleExit(@RequestBody @Valid ParkingExitRequest request) {
        ParkingEntryResponse response = exitService.vehicleExit(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ParkingEntryResponse>> getAllEntries(Pageable pageable) {
        return ResponseEntity.ok(entryService.getAllEntries(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<Page<ParkingEntryResponse>> getActives(Pageable pageable) {
        return ResponseEntity.ok(entryService.getActives(pageable));
    }
}
