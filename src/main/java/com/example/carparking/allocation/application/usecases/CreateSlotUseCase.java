package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateSlotUseCase {

    private final SlotRepository repository;

    @Transactional
    public Slot createSlot(@NotNull(message = "Slot code cannot be null.") String slotCode) {
        if (repository.existsBySlotCode(slotCode)) {
            throw new IllegalArgumentException("Slot already exists");
        }

        Slot newSlot = Slot.create(slotCode);

        return repository.save(newSlot);
    }
}
