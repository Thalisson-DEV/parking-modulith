package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.domain.Slot;
import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import com.example.carparking.event.VehicleEnteredEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotOccupationUseCase {

    private final SlotRepository repository;

    @EventListener
    public void handleVehicleEntry(VehicleEnteredEvent event) {
        Slot slot = repository.findFirstByAvailableTrue()
                .orElseThrow(() -> new EntityNotFoundException("No available slot found"));

        slot.occupationSlot(event.vehicleNumber());

        repository.save(slot);
    }
}
