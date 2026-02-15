package com.example.carparking.entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface ParkingEntryRepository extends JpaRepository<ParkingEntry, Long> {

    Optional<ParkingEntry> findByVehicleNumberAndActiveTrue(String vehicleNumber);

    Page<ParkingEntry> findAllByActiveTrue(Pageable pageable);
}
