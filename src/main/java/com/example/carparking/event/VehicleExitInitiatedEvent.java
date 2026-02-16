package com.example.carparking.event;

import java.time.LocalDateTime;

public record VehicleExitInitiatedEvent(Long parkingEntryId, String vehicleNumber, LocalDateTime entryTime) {
}
