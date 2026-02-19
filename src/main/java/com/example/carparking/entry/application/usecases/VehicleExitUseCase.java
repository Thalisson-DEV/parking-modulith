package com.example.carparking.entry.application.usecases;

import com.example.carparking.entry.domain.ParkingEntry;
import com.example.carparking.entry.infrastructure.percistence.ParkingEntryRepository;
import com.example.carparking.event.PaymentSuccessfulEvent;
import com.example.carparking.event.VehicleExitInitiatedEvent;
import com.example.carparking.event.VehicleExitedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleExitUseCase {

    private final ParkingEntryRepository repository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public ParkingEntry initiateVehicleExit(String vehicleNumber) {
        ParkingEntry entry = repository.findByVehicleNumberAndActiveTrue(vehicleNumber)
                .orElseThrow(() -> new EntityNotFoundException("Active parking entry not found for vehicle " + vehicleNumber));

        publisher.publishEvent(new VehicleExitInitiatedEvent(entry.getId(), entry.getVehicleNumber(), entry.getEntryTime()));
        return entry;
    }

    @EventListener
    @Transactional
    public void finalizeVehicleExit(PaymentSuccessfulEvent event) {
        ParkingEntry entry = repository.findById(event.parkingEntryId())
                .orElseThrow(() -> new EntityNotFoundException("Parking entry not found for ID " + event.parkingEntryId()));

        entry.recordExit();
        repository.save(entry);

        publisher.publishEvent(new VehicleExitedEvent(entry.getVehicleNumber(), entry.getEntryTime(), entry.getExitTime()));
    }
}

