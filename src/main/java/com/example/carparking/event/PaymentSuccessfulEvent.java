package com.example.carparking.event;

public record PaymentSuccessfulEvent(
        Long parkingEntryId,
        String vehicleNumber,
        double amount,
        Long billingRecordId) {
}
