package com.example.carparking.entry.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ParkingExitRequest(
        @NotBlank(message = "Vehicle number is required")
        String vehicleNumber
) {
}
