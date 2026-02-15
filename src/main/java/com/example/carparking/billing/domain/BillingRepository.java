package com.example.carparking.billing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<BillingRecord, Long> {
}
