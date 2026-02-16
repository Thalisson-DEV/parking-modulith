package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListSlotUseCase {

    private final SlotRepository repository;

    @Transactional(readOnly = true)
    public Page<Slot> getAllSlots(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Slot> getAvailableSlots(Pageable pageable) {
        return repository.findAllByAvailableTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Slot findBySlotCode(@NotNull(message = "Slot code cannot be null.") String slotCode) {
        return repository.findBySlotCode(slotCode)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));
    }
}
