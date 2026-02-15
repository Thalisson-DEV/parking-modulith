package com.example.carparking.entry.infrastructure.web;

import com.example.carparking.entry.infrastructure.web.dto.ParkingEntryResponse;
import com.example.carparking.entry.domain.model.ParkingEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingEntryMapper {

    ParkingEntryMapper INSTANCE = Mappers.getMapper(ParkingEntryMapper.class);

    ParkingEntryResponse toDto(ParkingEntry parkingEntry);
}
