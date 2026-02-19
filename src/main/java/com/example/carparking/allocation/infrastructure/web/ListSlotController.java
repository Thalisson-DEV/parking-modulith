package com.example.carparking.allocation.infrastructure.web;

import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import com.example.carparking.allocation.application.mappers.SlotMapper;
import com.example.carparking.allocation.application.usecases.ListSlotUseCase;
import com.example.carparking.allocation.domain.Slot;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ListSlotController {

    private final ListSlotUseCase listSlotUseCase;
    private final SlotMapper mapper;

    @GetMapping("/slot")
    public ResponseEntity<Page<SlotCreatedResponse>> getAllSlots(Pageable pageable) {
        Page<Slot> page = listSlotUseCase.getAllSlots(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/slot/available")
    public ResponseEntity<Page<SlotCreatedResponse>> getAvailableSlots(Pageable pageable) {
        Page<Slot> page = listSlotUseCase.getAvailableSlots(pageable);
        return ResponseEntity.ok(page.map(mapper::toDto));
    }

    @GetMapping("/slot/slot-code/{slotCode}")
    public ResponseEntity<SlotCreatedResponse> findBySlotCode(@PathVariable("slotCode") @Valid String slotCode) {
        Slot slot = listSlotUseCase.findBySlotCode(slotCode);
        return ResponseEntity.ok(mapper.toDto(slot));
    }
}
