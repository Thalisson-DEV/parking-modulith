package com.example.carparking.entry.dto;

import com.example.carparking.entry.ParkingEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingEntryMapper {

    ParkingEntryMapper INSTANCE = Mappers.getMapper(ParkingEntryMapper.class);

    ParkingEntryResponse toDto(ParkingEntry parkingEntry);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entryTime", ignore = true)
    @Mapping(target = "exitTime", ignore = true)
    @Mapping(target = "active", ignore = true)
    ParkingEntry toEntity(ParkingEntryRequest parkingEntryRequest);
}
