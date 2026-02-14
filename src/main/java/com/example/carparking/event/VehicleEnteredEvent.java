package com.example.carparking.event;

import java.time.LocalDateTime;

public record VehicleEnteredEvent (
        String vehicleNumber,
        LocalDateTime entryTime
) {}
