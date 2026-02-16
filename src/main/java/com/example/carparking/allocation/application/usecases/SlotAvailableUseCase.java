package com.example.carparking.allocation.application.usecases;

import com.example.carparking.allocation.infrastructure.percistence.SlotRepository;
import com.example.carparking.event.VehicleExitedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotAvailableUseCase {

    private final SlotRepository repository;

    @EventListener
    public void handlerVehicleExit(VehicleExitedEvent event) {
        repository.findByVehicleNumber(event.vehicleNumber())
                .ifPresentOrElse(slot -> {
                    slot.availableSlot();
                    repository.save(slot);
                }, () -> {
                    throw new EntityNotFoundException("Vehicle not found");
                });
    }
}
