package com.example.carparking.entry.dto;

import jakarta.validation.constraints.NotBlank;

public record ParkingEntryRequest(
        @NotBlank(message = "Vehicle number is required")
        String vehicleNumber
) {
}
