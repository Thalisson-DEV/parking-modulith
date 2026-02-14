package com.example.carparking.entry.dto;

import java.time.LocalDateTime;

public record ParkingEntryResponse(
        Long id,
        String vehicleNumber,
        LocalDateTime entryTime,
        LocalDateTime exitTime
) {
}
