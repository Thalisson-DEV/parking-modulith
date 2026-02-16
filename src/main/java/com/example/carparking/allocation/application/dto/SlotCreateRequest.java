package com.example.carparking.allocation.application.dto;

import jakarta.validation.constraints.NotNull;

public record SlotCreateRequest(
        @NotNull(message = "Slot code cannot be null")
        String slotCode
) {}
