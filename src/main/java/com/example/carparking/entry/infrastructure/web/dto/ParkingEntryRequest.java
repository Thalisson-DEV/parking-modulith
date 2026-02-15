package com.example.carparking.entry.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ParkingEntryRequest(
        @NotBlank(message = "Vehicle number is required")
        String vehicleNumber
) {
}
