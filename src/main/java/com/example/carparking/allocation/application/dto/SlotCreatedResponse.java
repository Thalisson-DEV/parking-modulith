package com.example.carparking.allocation.application.dto;

public record SlotCreatedResponse(
        Long id,
        String slotCode,
        Boolean available
) {}
