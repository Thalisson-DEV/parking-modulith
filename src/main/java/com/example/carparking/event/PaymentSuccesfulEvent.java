package com.example.carparking.event;

import java.time.Duration;

public record PaymantSuccesfulEvent(
        String vehicleNumber,
        Duration billingTime,
        Double amount
) {}
