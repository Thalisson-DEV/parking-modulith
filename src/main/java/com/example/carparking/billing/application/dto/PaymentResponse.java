package com.example.carparking.billing.application.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long parkingEntryId,
        String vehicleNumber,
        double amount,
        Duration billingTime,
        boolean paid,
        LocalDateTime paymentTime
) {
}
