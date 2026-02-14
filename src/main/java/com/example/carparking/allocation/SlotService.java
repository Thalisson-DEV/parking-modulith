package com.example.carparking.allocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository repository;

    public void createSlot(String slotCode) {
        repository.save(new Slot(null, slotCode, true, null));
    }
}
