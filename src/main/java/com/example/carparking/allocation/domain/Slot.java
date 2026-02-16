package com.example.carparking.allocation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity(name = "slot")
@Table(name = "slot")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slotCode;
    private boolean available;
    private String vehicleNumber;

    public static Slot create(@NotNull(message = "Slot code cannot be null") String slotCode) {
        Slot slot = new Slot();
        slot.slotCode = slotCode;
        slot.available = true;
        return slot;
    }

    public void occupationSlot(String vehicleNumber) {
        if (!this.available) {
            throw new IllegalStateException("Slot is already occupied.");
        }
        this.vehicleNumber = vehicleNumber;
        this.available = false;
    }

    public void availableSlot() {
        if (this.available) {
            throw new IllegalStateException("Slot is already free.");
        }
        this.vehicleNumber = null;
        this.available = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(id, slot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
