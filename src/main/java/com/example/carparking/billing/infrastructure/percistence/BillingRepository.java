package com.example.carparking.billing.infrastructure.percistence;

import com.example.carparking.billing.domain.BillingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<BillingRecord, Long> {
}
