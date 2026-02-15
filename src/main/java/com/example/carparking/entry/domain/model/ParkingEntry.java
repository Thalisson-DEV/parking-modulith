package com.example.carparking.entry.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "parking_entry")
@Table(name = "parking_entry")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private boolean active;

    public static ParkingEntry create(@NotNull(message = "Vehicle number cannot be null") String vehicleNumber) {
        ParkingEntry entry = new ParkingEntry();
        entry.vehicleNumber = vehicleNumber;
        entry.entryTime = LocalDateTime.now();
        entry.active = true;
        return entry;
    }

    public void recordExit() {
        if (!this.active) {
            throw new IllegalStateException("Cannot record exit for an inactive parking entry.");
        }
        this.active = false;
        this.exitTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingEntry that = (ParkingEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
