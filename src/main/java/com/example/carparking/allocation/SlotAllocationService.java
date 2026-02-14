package com.example.carparking.allocation;

import com.example.carparking.event.VehicleEnteredEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotAllocationService {

    private final SlotRepository repository;

    @EventListener
    public void handleVehicleEntry(VehicleEnteredEvent event) {
        Slot slot = repository.findFirstByAvailableTrue()
                .orElseThrow(() -> new EntityNotFoundException("No available slot found"));
        slot.setAvailable(false);
        slot.setVehicleNumber(event.vehicleNumber());

        repository.save(slot);
    }

    @EventListener
    public void handlerVehicleExit(VehicleEnteredEvent event) {
        repository.findByVehicleNumber(event.vehicleNumber())
                .ifPresentOrElse(slot -> {
                    slot.setAvailable(true);
                    slot.setVehicleNumber(null);
                    repository.save(slot);
                    System.out.println("Freed Slot" + slot.getSlotCode() + " for vehicle: " + event.vehicleNumber());
                }, () -> {
                    throw new EntityNotFoundException("Vehicle not found");
                });
    }
}
