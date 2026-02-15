package com.example.carparking.entry.api;

import com.example.carparking.entry.api.dto.ParkingEntryRequest;
import com.example.carparking.entry.api.dto.ParkingEntryResponse;
import com.example.carparking.entry.domain.ParkingEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingEntryMapper {

    ParkingEntryMapper INSTANCE = Mappers.getMapper(ParkingEntryMapper.class);

    ParkingEntryResponse toDto(ParkingEntry parkingEntry);
}
