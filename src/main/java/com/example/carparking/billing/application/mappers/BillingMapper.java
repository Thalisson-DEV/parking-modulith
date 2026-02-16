package com.example.carparking.billing.application.mappers;

import com.example.carparking.billing.application.dto.PaymentResponse;
import com.example.carparking.billing.domain.BillingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    BillingMapper INSTANCE = Mappers.getMapper(BillingMapper.class);

    PaymentResponse toDto(BillingRecord billingRecord);
}
