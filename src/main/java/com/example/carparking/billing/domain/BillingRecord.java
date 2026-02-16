package com.example.carparking.billing.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "billing")
@Table(name = "billing")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BillingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parkingEntryId;
    private String vehicleNumber;
    private double amount;
    private Duration billingTime;
    private boolean paid;
    private LocalDateTime paymentTime;

    public static BillingRecord create(
            @NotNull(message = "Parking Entry ID cannot be null")
            Long parkingEntryId,
            @NotNull(message = "Vehicle number cannot be null")
            String vehicleNumber,
            @NotNull(message = "Entry time cannot be null")
            LocalDateTime entryTime,
            @NotNull(message = "Exit time cannot be null")
            LocalDateTime exitTime
    ) {
        BillingRecord record = new BillingRecord();
        record.parkingEntryId = parkingEntryId;
        record.vehicleNumber = vehicleNumber;
        record.setAmountAndBillingTime(entryTime, exitTime);
        record.paid = false;
        record.paymentTime = null;
        return record;
    }

    private void setAmountAndBillingTime(LocalDateTime entryTime, LocalDateTime exitTime) {
        Duration duration = Duration.between(entryTime, exitTime);
        this.billingTime = duration;
        this.amount = Math.max(20, (duration.toMinutes() / 60.0) * 10);
    }

    public void markAsPaid() {
        if (this.paid) {
            throw new IllegalStateException("Billing record is already paid.");
        }
        this.paid = true;
        this.paymentTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingRecord that = (BillingRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
