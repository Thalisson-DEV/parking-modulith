package com.example.carparking.entry.infrastructure.percistence;

import com.example.carparking.entry.domain.ParkingEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingEntryRepository extends JpaRepository<ParkingEntry, Long> {

    Optional<ParkingEntry> findByVehicleNumberAndActiveTrue(String vehicleNumber);

    Page<ParkingEntry> findAllByActiveTrue(Pageable pageable);

    Optional<ParkingEntry> findByVehicleNumber(String vehicleNumber);

    boolean existsByVehicleNumberAndActiveTrue(String vehicleNumber);
}
