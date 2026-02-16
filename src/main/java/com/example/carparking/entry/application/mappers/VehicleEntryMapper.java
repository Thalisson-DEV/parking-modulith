package com.example.carparking.entry.application.mappers;

import com.example.carparking.entry.application.dto.ParkingEntryResponse;
import com.example.carparking.entry.domain.ParkingEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleEntryMapper {

    VehicleEntryMapper INSTANCE = Mappers.getMapper(VehicleEntryMapper.class);

    ParkingEntryResponse toDto(ParkingEntry parkingEntry);
}
