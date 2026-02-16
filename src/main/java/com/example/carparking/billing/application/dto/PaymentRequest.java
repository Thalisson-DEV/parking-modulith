package com.example.carparking.billing.application.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull(message = "Billing record ID is required")
        Long billingRecordId
) {
}
