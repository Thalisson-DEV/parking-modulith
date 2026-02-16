package com.example.carparking.allocation.infrastructure.percistence;

import com.example.carparking.allocation.domain.Slot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    Optional<Slot> findFirstByAvailableTrue();

    Optional<Slot> findByVehicleNumber(String vehicleNumber);

    boolean existsBySlotCode(String slotCode);

    Page<Slot> findAllByAvailableTrue(Pageable pageable);

    Optional<Slot> findBySlotCode(String slotCode);
}
