package com.example.carparking.entry.dto;

import java.time.LocalDateTime;

public record ParkingExitResponse(
        Long id,
        String vehicleNumber,
        LocalDateTime entryTime,
        LocalDateTime exitTime
) {
}
