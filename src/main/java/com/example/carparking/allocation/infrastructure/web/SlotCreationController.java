package com.example.carparking.allocation.infrastructure.web;

import com.example.carparking.allocation.doc.SlotCreationControllerDocs;
import com.example.carparking.allocation.application.dto.SlotCreateRequest;
import com.example.carparking.allocation.application.dto.SlotCreatedResponse;
import com.example.carparking.allocation.application.mappers.SlotMapper;
import com.example.carparking.allocation.application.usecases.CreateSlotUseCase;
import com.example.carparking.allocation.domain.Slot;
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
public class SlotCreationController implements SlotCreationControllerDocs {

    private final CreateSlotUseCase createSlotUseCase;
    private final SlotMapper mapper;

    @PostMapping("/slot")
    public ResponseEntity<SlotCreatedResponse> createSlot(@Valid @RequestBody SlotCreateRequest request) {
        Slot newSlot = createSlotUseCase.createSlot(request.slotCode());
        SlotCreatedResponse response = mapper.toDto(newSlot);
        return ResponseEntity.ok(response);
    }
}
